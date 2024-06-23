package com.example.dp.core.utils

import android.content.Context
import com.example.dp.core.utils.ScheduleSubject.Companion.dayOrderToTime
import com.example.dp.data.DataBase
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.AttendanceEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.pojo.GroupPOJO
import org.json.JSONObject
import java.io.InputStream
import java.time.DayOfWeek
import java.time.ZoneId
import javax.inject.Inject


const val semesterStartDate = 1713128400000
const val semesterEndDate = 1723669200000

class AssetProvider @Inject constructor(private val context: Context) {

    fun getAsset(fileName: String): InputStream {
        return context.assets.open(fileName)
    }
}

class JSONLoader {
    fun load(inputStream: InputStream): JSONObject {
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        return JSONObject(String(buffer, Charsets.UTF_8))
    }
}


data class ScheduleDay(
    val dayOfWeek: DayOfWeek,
    val isUpperWeek: Boolean,
    val subjects: List<ScheduleSubject>
)

data class ScheduleSubject(
    val activityType: Int,
    val subjectMetaID: Int,
    val teacherMetaID: Int,
    val dayScheduleOrder: Int
) {
    companion object {
        fun Int.dayOrderToTime() = when (this) {
            1    -> 8 * 3600000
            2    -> 10 * 3600000
            3    -> 12 * 3600000
            4    -> 14 * 3600000
            else -> 16 * 3600000
        }
    }
}

suspend fun generateGroupSchedule(group: GroupPOJO, dataBase: DataBase) {
    val semesterSchedule = mutableListOf<ScheduleDay>()
    for (i in 1..6) {
        semesterSchedule.add(generateDaySchedule(DayOfWeek.of(i), true))
        semesterSchedule.add(generateDaySchedule(DayOfWeek.of(i), false))
    }

    val startOfSemester = semesterStartDate.toLocalDate()
    val endOfSemester = semesterEndDate.toLocalDate()

    var subjectID = when (group.id) {
        1    -> 10000
        2    -> 20000
        else -> 30000
    }

    val subjectsList = mutableListOf<SubjectEntity>()
    val absenceList = mutableListOf<AbsenceEntity>()
    val attendanceList = mutableListOf<AttendanceEntity>()

    semesterSchedule.forEach { scheduleDay ->
        val dates = createListOfDates(
            startDate = startOfSemester,
            endDate = endOfSemester,
            dayOfWeek = scheduleDay.dayOfWeek,
            isUpperWeek = scheduleDay.isUpperWeek
        )
        dates.forEach { date ->
            val epochMilliseconds = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

            scheduleDay.subjects.forEach { scheduleSubject ->
                val subject = SubjectEntity(
                    id = subjectID++,
                    groupID = group.id!!,
                    teacherID = scheduleSubject.teacherMetaID,
                    subjectMetadataID = scheduleSubject.subjectMetaID,
                    date = epochMilliseconds + scheduleSubject.dayScheduleOrder.dayOrderToTime(),
                    dayScheduleOrder = scheduleSubject.dayScheduleOrder,
                    activityType = scheduleSubject.activityType,
                )
                subjectsList.add(subject)

                group.members.forEach { user ->
                    val chance = (0..10).random()
                    if (chance > 8) {
                        absenceList.add(AbsenceEntity(userID = user.id!!, subjectID = subject.id!!))
                    } else {
                        attendanceList.add(AttendanceEntity(userID = user.id!!, subjectID = subject.id!!))
                    }
                }
            }
        }
    }

    dataBase.scheduleDAO.createSubjects(subjectsList)
    dataBase.scheduleDAO.createAbsences(absenceList)
    dataBase.scheduleDAO.createAttendances(attendanceList)
}

fun generateDaySchedule(dayOfWeek: DayOfWeek, isUpperWeek: Boolean): ScheduleDay {
    val subjects = mutableListOf<ScheduleSubject>()
    for (i in 1..5) {
        if ((1..100).random() > 25) {
            val teacherID = (1..10).random()
            subjects.add(
                ScheduleSubject(
                    activityType = (1..3).random(),
                    subjectMetaID = teacherID,
                    teacherMetaID = teacherID,
                    dayScheduleOrder = i
                )
            )
        }
    }
    return ScheduleDay(
        dayOfWeek = dayOfWeek,
        isUpperWeek = isUpperWeek,
        subjects = subjects
    )
}