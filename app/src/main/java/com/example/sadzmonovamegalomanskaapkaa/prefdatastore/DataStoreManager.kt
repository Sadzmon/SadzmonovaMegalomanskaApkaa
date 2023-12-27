import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DataStoreManager( private val context: Context ) {

    // to make sure there's only one instance
    companion object {
        private val Context.dataStoree: DataStore<Preferences> by preferencesDataStore("merchant_datastore")
        val ALARM_0       = stringPreferencesKey("ALARM_0")
        val ALARM_1       = stringPreferencesKey("ALARM_1")
        val ALARM_2       = stringPreferencesKey("ALARM_2")
        val ALARM_3       = stringPreferencesKey("ALARM_3")
        val DOORBELL_0    = stringPreferencesKey("DOORBELL_0")
        val DOORBELL_1    = stringPreferencesKey("DOORBELL_1")
        val DOORBELL_2    = stringPreferencesKey("DOORBELL_2")
        val DOORBELL_3    = stringPreferencesKey("DOORBELL_3")
        val KETTLE_0      = stringPreferencesKey("KETTLE_0")
        val KETTLE_1      = stringPreferencesKey("KETTLE_1")
        val KETTLE_2      = stringPreferencesKey("KETTLE_2")
        val KETTLE_3      = stringPreferencesKey("KETTLE_3")
        val FRIDGE_0      = stringPreferencesKey("FRIDGE_0")
        val FRIDGE_1      = stringPreferencesKey("FRIDGE_1")
        val FRIDGE_2      = stringPreferencesKey("FRIDGE_2")
        val FRIDGE_3      = stringPreferencesKey("FRIDGE_3")
        val ALARMCOUNT    = stringPreferencesKey("ALARMCOUNT")
        val DOORBELLCOUNT = stringPreferencesKey("DOORBELLCOUNT")
        val KETTLECOUNT   = stringPreferencesKey("KETTLECOUNT")
        val FRIDGECOUNT   = stringPreferencesKey("FRIDGECOUNT")
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

        val getAlarm: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ FridgeIndex ] ?: "false"
            }

        return getAlarm.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting values from KETTLE
    @Composable
    fun getKettle( index: String ): Boolean
    {
        val kettleIndex = stringPreferencesKey("KETTLE_" + index)

        val getAlarm: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ kettleIndex ] ?: "false"
            }

        return getAlarm.collectAsState(initial = "").value!!.toBoolean()
    }

    //function for getting value from DOORBELL
    @Composable
    fun getDoorbell( index: String ): Boolean
    {
        val doorbellIndex = stringPreferencesKey( "DOORBELL_" + index )

        val getAlarm: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ doorbellIndex ] ?: "false"
            }

        return getAlarm.collectAsState(initial = "").value!!.toBoolean()
    }


    //function for getting value from ALARMCOUNT
    @Composable
    fun getAlarmCount(): String
    {
        val alarmCount = stringPreferencesKey( "ALARMCOUNT" )

        val getAlarmCount: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ alarmCount ] ?: "0"
            }

        return getAlarmCount.collectAsState(initial = "").value!!
    }

    //function for getting value from ALARMCOUNT
    @Composable
    fun getDoorbellCount(): String
    {
        val alarmCount = stringPreferencesKey( "ALARMCOUNT" )

        val getAlarmCount: Flow<String?> = context.dataStoree.data
            .map { preferences ->
                preferences[ alarmCount ] ?: "0"
            }

        return getAlarmCount.collectAsState(initial = "").value!!
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
    suspend fun saveAlarmCount( amount: Int ) {
        val alarmAmount =  stringPreferencesKey("ALARMCOUNT" )
        context.dataStoree.edit { preferences ->
            preferences[ alarmAmount ] = amount.toString()
        }
    }
}