package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

class MockDataLoader {
    fun loadMockData() {
        val tasksDao = Database.getInstance().getTasksDAO()
        val usersDao = Database.getInstance().getUsersDAO()
        val activitiesDAO = Database.getInstance().getActivitiesDAO()
        if (tasksDao.getAllTasks().isEmpty() &&
            usersDao.getAllUsers().isEmpty()
        ) {
            val users = arrayOf(

                User(0, "matej", "ritosa", "mritosa20@student.foi.hr", "123456"),
                User(0, "Nikola", "Parag", "niki.parag@gmail.com", "123456")
            )
            usersDao.insertUser(*users)
            val dbUsers = usersDao.getAllUsers()
            val tasks = arrayOf(
                Task(0, "Submit seminar paper", dbUsers[0].id, false),
                Task(0, "Prepare for exercises",dbUsers[0].id, false),
                Task(0, "Rally a project team", dbUsers[1].id, false),
                Task(0, "Work on 1st homework", dbUsers[1].id, false)
            )
            tasksDao.insertTask(*tasks)
        }
        if(activitiesDAO.getAllActivities().isEmpty()){
            val dbUsers = usersDao.getAllUsers()
            val activities = arrayOf(
                Activity(0,"Sastanak","2023-01-16 07:00:00","2023-01-16 08:00:00",dbUsers[0].id),
                Activity(0,"Sastanak","2023-01-17 07:00:00","2023-01-17 08:00:00",dbUsers[0].id),
                Activity(0,"Sastanak","2023-01-18 07:00:00","2023-01-18 08:00:00",dbUsers[0].id),
                Activity(0,"Sastanak","2023-01-19 07:00:00","2023-01-19 08:00:00",dbUsers[0].id),
                Activity(0,"Sastanak","2023-01-20 07:00:00","2023-01-20 08:00:00",dbUsers[0].id),

                Activity(0,"Sastanak s klijentom","2023-01-16 10:00:00","2023-01-16 12:00:00",dbUsers[0].id),
                Activity(0,"Pregeld odrađenog rada","2023-01-17 10:00:00","2023-01-17 12:00:00",dbUsers[0].id),
                Activity(0,"Pregeld odrađenog rada","2023-01-18 10:00:00","2023-01-18 12:00:00",dbUsers[0].id),
                Activity(0,"Sastanak s klijentom","2023-01-19 10:00:00","2023-01-19 12:00:00",dbUsers[0].id),
                Activity(0,"Pregeld odrađenog rada","2023-01-20 10:00:00","2023-01-20 12:00:00",dbUsers[0].id),


            )
            activitiesDAO.insertActivity(*activities)
        }
    }
}