package com.example.dp

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dp.core.ui.BaseActivity
import com.example.dp.core.utils.JSONLoader
import com.example.dp.core.utils.appComponent
import com.example.dp.core.utils.generateGroupSchedule
import com.example.dp.data.model.GroupEntity
import com.example.dp.data.model.SubjectMetadataEntity
import com.example.dp.data.model.TeacherMetadataEntity
import com.example.dp.data.model.UserEntity
import com.example.dp.databinding.ActivityInitializationBinding
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
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

            for (i in (1..3)) {
                generateGroupSchedule(group = groupDAO.getGroup(i), dataBase = this)
            }
        }
    }
}
