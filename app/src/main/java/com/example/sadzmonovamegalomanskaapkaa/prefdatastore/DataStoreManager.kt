import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager( private val context: Context ) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("merchant_datastore")
        val ALARM_COUNT    = intPreferencesKey("CA")
        val DOORBELL_COUNT = intPreferencesKey("CD")
        val FRIDGE_COUNT   = intPreferencesKey("FC")
        val KETTLE_COUNT   = intPreferencesKey("CK")
    }

    //function for getting values from ALARM
    @Composable
    fun getAlarm( index: Int ): Boolean
    {
        val AlarmIndex = stringPreferencesKey("ALARM_" + index.toString())

    val getAlarm: Flow<String?> = context.dataStoree.data
        .map { preferences ->
            preferences[ AlarmIndex ] ?: "false"
        }

        return getAlarm.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting values from FRIDGE
    @Composable
    fun getFridge( index: String ): Boolean
    {
        val FridgeIndex = stringPreferencesKey("FRIDGE_" + index )

        val getFridge: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ FridgeIndex ] ?: "false"
            }

        return getFridge.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting values from KETTLE
    @Composable
    fun getKettle( index: String ): Boolean
    {
        val kettleIndex = stringPreferencesKey("KETTLE_" + index)

        val getKettle: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ kettleIndex ] ?: "false"
            }

        return getKettle.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting value from DOORBELL
    @Composable
    fun getDoorbell( index: String ): Boolean
    {
        val doorbellIndex = stringPreferencesKey( "DOORBELL_" + index )

        val getDoorbell: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ doorbellIndex ] ?: "false"
            }

        return getDoorbell.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting value from KETTLE_COUNT
    @Composable
    fun getKettleCount(): Int
    {
        val getKettleCount: Flow<Int?> = context.dataStoree.data
            .map { preferences ->
                preferences[ KETTLE_COUNT ] ?: 0
            }

        return getKettleCount.collectAsState(initial = 0).value!!
    }

    @Composable
    fun getAlarmCount(): Int
    {
        val getAlarmCount: Flow<Int> = context.dataStoree.data
            .map { preferences ->
                // No type safety.
                preferences[ ALARM_COUNT ] ?: 0
            }

        return getAlarmCount.collectAsState(initial = 0).value!!
    }
    //function for getting value from FRIDGE_COUNT
    @Composable
    fun getFridgeCount(): Int
    {
        val getFridgeCount: Flow<Int?> = context.dataStoree.data
            .map { preferences ->
                preferences[ FRIDGE_COUNT ] ?: 0
            }

        return getFridgeCount.collectAsState(initial = 0).value!!
    }
    //function for getting value from DOORBELL_COUNT
    @Composable
    fun getDoorbellCount(): Int
    {
        val getDoorbellCount: Flow<Int?> = context.dataStoree.data
            .map { preferences ->
                preferences[ DOORBELL_COUNT ] ?: 0
            }

        return getDoorbellCount.collectAsState(initial = 0).value!!
    }

    //save alarm[i] into datastore
    suspend fun saveAlarm( index: Int, value: String ) {
        val index =  stringPreferencesKey("ALARM_" + index.toString() )
        context.dataStoree.edit { preferences ->
            preferences[ index ] = value
        }
    }

    //save fridge[i] into datastore
    suspend fun saveFridge( index: Int, value: String ) {
        val index =  stringPreferencesKey("FRIDGE_" + index.toString() )
        context.dataStoree.edit { preferences ->
            preferences[ index ] = value
        }
    }

    //save kettle[i] into datastore
    suspend fun saveKettle( index: Int, value: String ) {
        val index =  stringPreferencesKey("Kettle_" + index.toString() )
        context.dataStoree.edit { preferences ->
            preferences[ index ] = value
        }
    }

    //save doorbell[i] into datastore
    suspend fun saveDoorbell( index: Int, value: String ) {
        val index =  stringPreferencesKey("DOORBELL_" + index.toString() )
        context.dataStoree.edit { preferences ->
            preferences[ index ] = value
        }
    }

    //save doorbell[i] into

   suspend fun saveAlarmCount( count: Int ) {
        context.dataStoree.edit { preferences ->
            preferences[ ALARM_COUNT ] = count
        }
    }
    suspend fun saveKettleCount( count: Int ) {
        context.dataStoree.edit { preferences ->
            preferences[ KETTLE_COUNT ] = count
        }
    }
    suspend fun saveFridgeCount( count: Int ) {
        context.dataStoree.edit { preferences ->
            preferences[ FRIDGE_COUNT ] = count
        }
    }
    suspend fun saveDoorbellCount( count: Int ) {
        context.dataStoree.edit { preferences ->
            preferences[ DOORBELL_COUNT ] = count
        }
    }
}
