package com.example.dp.ui.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dp.core.utils.DataSource
import com.example.dp.core.utils.PrefUtils
import com.example.dp.core.utils.fetchLocal
import com.example.dp.data.State
import com.example.dp.data.dao.ScheduleDAO
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.ui.subject.SubjectUIModel.Companion.toUIModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SubjectViewModel @Inject constructor(
    private val scheduleDAO: ScheduleDAO,
    private val prefUtils: PrefUtils,
) : ViewModel() {

    var subjectID: Int = -1
    var isFutureDate: Boolean = false
    var isUserCanEdit: Boolean = false

    val groupInfo: SharedFlow<State<SubjectUIModel>> by lazy {
        fetchLocal(
            dataProvider = { DataSource.LocalFlow(scheduleDAO.getSubjectFlow(subjectID)) },
            mapDelegate = { subject ->
                val userIsAdmin = subject.group.adminID == prefUtils.userID
                val userIsTeacher = subject.group.members.any { user ->
                    user.id == prefUtils.userID && user.userType == 2
                }
                isFutureDate = subject.date <= System.currentTimeMillis()
                isUserCanEdit = isFutureDate && (userIsAdmin || userIsTeacher)
                subject.toUIModel()
            }
        ).shareIn(
            viewModelScope, SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000), 1
        )
    }

    fun onAbsenceClicked(user: SubjectUIModel.UserUIModel) {
        viewModelScope.launch {
            if (user.absence != null) {
                scheduleDAO.deleteAbsence(user.absence.id!!)
            } else {
                scheduleDAO.createAbsence(
                    AbsenceEntity(
                        userID = user.ID,
                        subjectID = subjectID,
                    )
                )
            }
        }
    }
}