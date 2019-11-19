package server;

public interface Constants {
    static final int MAX_THREADS = 10;
    static final int PORT_NUM = 9000;

    enum Commands{login, logout, quit, search}
}
