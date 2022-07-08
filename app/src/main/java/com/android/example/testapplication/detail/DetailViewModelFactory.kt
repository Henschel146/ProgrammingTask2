import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.example.testapplication.databases.UserDao
import com.android.example.testapplication.detail.DetailViewModel
import com.android.example.testapplication.domain.UserDomain
import com.android.example.testapplication.network.User

class DetailViewModelFactory(
    private val user: UserDomain, private val dataSource: UserDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(user,dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}