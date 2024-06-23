package com.example.dp.di.module

import androidx.lifecycle.ViewModel
import com.example.dp.ui.create_group.CreateGroupViewModel
import com.example.dp.ui.group.GroupViewModel
import com.example.dp.ui.group_list.GroupListViewModel
import com.example.dp.ui.schedule.ScheduleViewModel
import com.example.dp.ui.home.HomeViewModel
import com.example.dp.ui.lessons_info.LessonsInfoViewModel
import com.example.dp.ui.profile.ProfileViewModel
import com.example.dp.ui.sign_in.SignInViewModel
import com.example.dp.ui.sign_up.SignUpViewModel
import com.example.dp.ui.subject.SubjectViewModel
import com.example.dp.ui.userInfo.UserInfoViewModel
import com.example.dp.ui.user_absence.UserAbsenceViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)


@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindsScheduleViewModel(viewModel: ScheduleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindsProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindsSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindsSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupViewModel::class)
    abstract fun bindsGroupViewModel(viewModel: GroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserInfoViewModel::class)
    abstract fun bindsUserInfoViewModel(viewModel: UserInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserAbsenceViewModel::class)
    abstract fun bindsUserAbsenceViewModel(viewModel: UserAbsenceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GroupListViewModel::class)
    abstract fun bindsGroupListViewModel(viewModel: GroupListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CreateGroupViewModel::class)
    abstract fun bindsCreateGroupViewModel(viewModel: CreateGroupViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LessonsInfoViewModel::class)
    abstract fun bindsLessonsInfoViewModel(viewModel: LessonsInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SubjectViewModel::class)
    abstract fun bindsSubjectViewModel(viewModel: SubjectViewModel): ViewModel
}