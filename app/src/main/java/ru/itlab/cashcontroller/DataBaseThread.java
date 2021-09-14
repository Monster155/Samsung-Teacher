package ru.itlab.cashcontroller;

import java.util.List;

public class DataBaseThread extends Thread {

    private boolean isFinished;
    private UserDao userDao;

    public DataBaseThread(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void run() {
        User u1 = new User();
        u1.firstName = "Damir";
        u1.lastName = "Dav";

        User u2 = new User();
        u2.firstName = "Bulat";
        u2.lastName = "Fah";

        userDao.insertAll(u1, u2);

        List<User> users = userDao.getAll();
        System.out.println("Users: ");
        for (User u : users) {
            System.out.println(u);
        }
        isFinished = true;
    }

    public boolean getIsFinished() {
        return isFinished;
    }
}
