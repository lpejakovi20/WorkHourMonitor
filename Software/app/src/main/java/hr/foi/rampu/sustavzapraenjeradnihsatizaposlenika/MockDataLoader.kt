package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

class MockDataLoader {
    fun loadMockData() {
        val tasksDao = Database.getInstance().getTasksDAO()
        val usersDao = Database.getInstance().getUsersDAO()
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
    }
}