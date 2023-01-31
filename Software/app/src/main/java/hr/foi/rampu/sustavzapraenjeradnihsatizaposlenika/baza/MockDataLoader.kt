package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.*

class MockDataLoader {
    fun loadMockData() {
        val tasksDao = Database.getInstance().getTasksDAO()
        val usersDao = Database.getInstance().getUsersDAO()
        val statsDao = Database.getInstance().getStatsDAO()
        val activitiesDAO = Database.getInstance().getActivitiesDAO()

        if (tasksDao.getAllTasks().isEmpty() &&
            usersDao.getAllUsers().isEmpty()
        ) {

            val users = arrayOf(
                User(0, "Lovro", "Pejaković", "lpejakovi20@student.foi.hr", "123456",2,false),
                User(0, "Nikola", "Parag", "nparag20@student.foi.hr", "123456",2,false),
                User(0, "Projekt", "Rampu", "projektrampu@gmail.com", "123456",1,false)
            )
            usersDao.insertUser(*users)
            val dbUsers = usersDao.getAllUsers()
            val tasks = arrayOf(
                Task(0, "Riješiti projekt iz RAMPU", dbUsers[1].id, false),
                Task(0, "Proći završnu obranu",dbUsers[1].id, false),
                Task(0, "Riješiti RPP", dbUsers[1].id, false),
                Task(0, "Proći završnu obranu", dbUsers[1].id, false)
            )
            tasksDao.insertTask(*tasks)

            if(statsDao.getAllStats().isEmpty()){
                val stats = arrayOf(
                    Stats(0, "2023-01-10 09:20:00","2023-01-10 14:20:00",5,1,0,2),
                    Stats(0, "2023-01-12 09:20:00","2023-01-12 14:20:00",5,2,0,2)
                )
                statsDao.insertStats(*stats)
            }
            if(activitiesDAO.getAllActivities().isEmpty()){
                val dbUsers = usersDao.getAllUsers()
                val activities = arrayOf(
                    Activity(0,"Sastanak","2023-01-30 07:00:00","2023-01-30 08:00:00",dbUsers[1].id),
                    Activity(0,"Sastanak","2023-01-31 07:00:00","2023-01-31 08:00:00",dbUsers[1].id),
                    Activity(0,"Sastanak","2023-02-01 07:00:00","2023-02-01 08:00:00",dbUsers[1].id),
                    Activity(0,"Sastanak","2023-02-02 07:00:00","2023-02-02 08:00:00",dbUsers[1].id),
                    Activity(0,"Sastanak","2023-02-03 07:00:00","2023-02-03 08:00:00",dbUsers[1].id),

                    Activity(0,"Sastanak s klijentom","2023-01-30 10:00:00","2023-01-30 12:00:00",dbUsers[1].id),
                    Activity(0,"Pregeld odrađenog rada","2023-01-31 10:00:00","2023-01-31 12:00:00",dbUsers[1].id),
                    Activity(0,"Pregeld odrađenog rada","2023-02-01 10:00:00","2023-02-01 12:00:00",dbUsers[1].id),
                    Activity(0,"Sastanak s klijentom","2023-02-19 02:00:00","2023-02-02 12:00:00",dbUsers[1].id),
                    Activity(0,"Pregeld odrađenog rada","2023-02-03 10:00:00","2023-02-03 12:00:00",dbUsers[1].id),
                    )
                activitiesDAO.insertActivity(*activities)
            }
        }

        if(Database.getInstance().getJobStatusesDAO().getAllJobStatuses().isEmpty()){
            val jobStatuses = arrayOf(
                JobStatus(0,"2023-01-16 07:00:00",1),
            JobStatus(0,"2023-01-17 07:00:00",2)
            )
            Database.getInstance().getJobStatusesDAO().insertJobStatus(*jobStatuses)
        }

        val dbUsers = usersDao.getAllUsers()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (userSnapshot in dataSnapshot.children) {
                    val user = User(
                        email = userSnapshot.child("e_email").getValue(String::class.java) ?: "",
                        name = userSnapshot.child("e_name").getValue(String::class.java) ?: "",
                        password = userSnapshot.child("e_password").getValue(String::class.java) ?: "",
                        role = userSnapshot.child("e_role").getValue(Int::class.java) ?: 0,
                        surname = userSnapshot.child("e_surname").getValue(String::class.java) ?: "",
                        onJob = userSnapshot.child("e_onJob").getValue(Boolean::class.java) ?: false
                    )
                    if(user != null && dbUsers.find {
                        it.email == user.email
                        } == null ){
                        val newUser = arrayOf(User(0,user.name,user.surname,user.email,user.password,user.role,user.onJob))
                        Database.getInstance().getUsersDAO().insertUser(*newUser)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}