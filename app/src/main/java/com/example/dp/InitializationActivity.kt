package com.example.dp

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dp.core.ui.BaseActivity
import com.example.dp.core.utils.JSONLoader
import com.example.dp.core.utils.appComponent
import com.example.dp.data.model.AbsenceEntity
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.SubjectEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity
import com.example.dp.data.model.UserEntity
import com.example.dp.databinding.ActivityInitializationBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class InitializationActivity : BaseActivity<ActivityInitializationBinding>(
    ActivityInitializationBinding::inflate
) {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                prepopulateDB()
            }
            startActivity(Intent(this@InitializationActivity, MainActivity::class.java))
            finish()
        }
    }

    private suspend fun prepopulateDB() {
        delay(1000) //fake loading timer

        appComponent.dataBase.apply {
            clearAllTables()  //TODO remove after tests

            val jsonLoader = JSONLoader()
            val gson = GsonBuilder().create()

            val dbGroups = jsonLoader.load(
                appComponent.assetProvider.getAsset("db_groups_asset.json")
            )
            gson.fromJson<List<GroupEntity>>(
                dbGroups.get("data").toString(),
                object : TypeToken<List<GroupEntity>>() {}.type
            ).forEach {
                groupDAO.createGroup(it)
            }

            val dbUsers = jsonLoader.load(
                appComponent.assetProvider.getAsset("db_users_asset.json")
            )
            gson.fromJson<List<UserEntity>>(
                dbUsers.get("data").toString(),
                object : TypeToken<List<UserEntity>>() {}.type
            ).forEach {
                userDAO.createUser(it)
            }

            val dbSubjectMeta = jsonLoader.load(
                appComponent.assetProvider.getAsset("db_subject_meta_asset.json")
            )
            gson.fromJson<List<SubjectMetadataEntity>>(
                dbSubjectMeta.get("data").toString(),
                object : TypeToken<List<SubjectMetadataEntity>>() {}.type
            ).forEach {
                scheduleDAO.createSubjectMeta(it)
            }

            val dbTeacherMeta = jsonLoader.load(
                appComponent.assetProvider.getAsset("db_teacher_meta_asset.json")
            )
            gson.fromJson<List<TeacherMetadataEntity>>(
                dbTeacherMeta.get("data").toString(),
                object : TypeToken<List<TeacherMetadataEntity>>() {}.type
            ).forEach {
                scheduleDAO.createTeacherMeta(it)
            }

            val dbSubjects = jsonLoader.load(
                appComponent.assetProvider.getAsset("db_subjects_asset.json")
            )
            gson.fromJson<List<SubjectEntity>>(
                dbSubjects.get("data").toString(),
                object : TypeToken<List<SubjectEntity>>() {}.type
            ).forEach {
                scheduleDAO.createSubject(it)
            }

            gson.fromJson<List<SubjectEntity>>(
                dbSubjects.get("data").toString(),
                object : TypeToken<List<SubjectEntity>>() {}.type
            ).forEach { subject ->
                val group = groupDAO.getGroup(subject.groupID)
                group.members.forEach { user ->
                    val chance = (0..10).random()
                    if (chance > 8) {
                        scheduleDAO.createAbsence(
                            AbsenceEntity(
                                userID = user.id!!,
                                subjectID = subject.id!!,
                            )
                        )
                    }
                }
            }
        }
    }
}