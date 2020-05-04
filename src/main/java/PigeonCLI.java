import db.models.Room;
import db.models.User;
import services.datadase.RoomServise;
import services.datadase.UserServise;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PigeonCLI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {



        if (args.length < 1) {
            printOptions();
        }

        if (args.length > 0) {
            List<String> option = Arrays.asList(args);
            if (option.get(0).equals("-add")) {
                if (option.size() < 3 && option.size() > 1) {

                    String login = option.get(1);
                    System.out.println("write other data of user");
                    login = login.toLowerCase();
                    System.out.println("login: " + login);

                    System.out.print("nickName: ");
                    String nickName = scanner.nextLine().toLowerCase().trim();

                    System.out.print("name: ");
                    String name = scanner.nextLine().toLowerCase().trim();

                    System.out.print("surname: ");
                    String surname = scanner.nextLine().toLowerCase().trim();

                    System.out.print("password: ");
                    String password = scanner.nextLine().trim();

                    if (valid(login, nickName, name, surname, password)) {

                        UserServise userServise = new UserServise();
                        User system = userServise.findUserById(1);
                        User user = new User(name, surname, nickName, login, password, " ");
                        userServise.save(user);

                        Room room = new Room("system_" + nickName);

                        RoomServise roomServise = new RoomServise();

                        roomServise.save(room, system, user);

                        System.out.println("user is created");

                    } else {
                        System.out.println("wrong data try again");
                    }

                } else {
                    printOptions();
                }
            }else
            if (option.get(0).equals("-help")) {
                printOptions();
            } else {
                printOptions();
            }
        }

    }

    private static boolean valid(String login, String nickName, String name, String surname, String password) {

        boolean bLogin = false, bNick = false, bName = false, bSurname = false, bPassword = false;

        UserServise userServise = new UserServise();

        if (nickName.length() > 3 && nickName.length() < 10) {
            List<User> users = userServise.findUserByNick(nickName);
            bNick = users.isEmpty();
            if (!bNick)
                System.out.println("current nick name is already used");
        }

        if (name.length() > 3 && name.length() < 10) {
            bName = true;
            if (!bName) {
                System.out.println("name length should be between 3-10 symbols");
            }
        }

        if (surname.length() > 3 && surname.length() < 10) {
            bSurname = true;
            if (!bSurname) {
                System.out.println("surname length should be between 3-10 symbols");
            }
        }

        if (password.length() > 8 && password.length() < 15) {
            bPassword = true;
            if (!bPassword) {
                System.out.println("password length should be between 8-15 symbols");

            }
        }

        if (login.length() >3  && login.length() <10 ) {
            List<User> users = userServise.findUserByLogin(login);
            bLogin = users.isEmpty();

            if (!bLogin)
                System.out.println("current login is already used");
        }

        return bLogin && bNick && bName && bSurname && bPassword;
    }

    private static void printOptions() {
        System.out.println("-add  <user login>          add user to Mail Pigeon");
        System.out.println("-help                       show menu info");
    }

}
