package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

class MockDataLoader {
    fun loadMockData() {
        val tasksDao = Database.getInstance().getTasksDAO()
        val usersDao = Database.getInstance().getUsersDAO()
        val statsDao = Database.getInstance().getStatsDAO()

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

            if(statsDao.getAllStats().isEmpty()){
                val stats = arrayOf(Stats(0, "2023-01-10 09:20:00","2023-01-10 14:20:00",5,1,0,1),
                    Stats(0, "2023-01-12 09:20:00","2023-01-12 14:20:00",5,2,0,1)
                )
                statsDao.insertStats(*stats)
            }
        }
    }
}