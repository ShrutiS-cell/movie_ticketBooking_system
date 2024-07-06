package Movie_ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class movieTBooking {
    private static List<User> users = new ArrayList<>();
    private static Admin admin = new Admin();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample data
        admin.addMovie(new Movie("Inception", "18:00"));
        admin.addMovie(new Movie("Interstellar", "20:00"));

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    register(scanner);
                    break;
                case 2:
                    login(scanner);
                    break;
                case 3:
                    adminLogin(scanner);
                    break;
                case 4:
                    System.exit(0);
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        System.out.println("User registered successfully.");
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = findUser(username, password);
        if (user != null) {
            userMenu(scanner, user);
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private static void adminLogin(Scanner scanner) {

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();
        if ("admin".equals(password)) {
            adminMenu(scanner);
        } else {
            System.out.println("Invalid admin password.");
        }
    }

    private static User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    private static void userMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("1. View Movies");
            System.out.println("2. Book a Movie");
            System.out.println("3. View Booking History");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewMovies();
                    break;
                case 2:
                    bookMovie(scanner, user);
                    break;
                case 3:
                    viewBookingHistory(user);
                    break;
                case 4:
                    return;
            }
        }
    }

    private static void adminMenu(Scanner scanner) {
        while (true) {
            System.out.println("1. Add Movie");
            System.out.println("2. View Movies");
            System.out.println("3. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addMovie(scanner);
                    break;
                case 2:
                    viewMovies();
                    break;
                case 3:
                    return;
            }
        }
    }

    private static void addMovie(Scanner scanner) {
        System.out.print("Enter movie title: ");
        String title = scanner.nextLine();
        System.out.print("Enter showtime: ");
        String showtime = scanner.nextLine();
        admin.addMovie(new Movie(title, showtime));
        System.out.println("Movie added successfully.");
    }

    private static void viewMovies() {
        List<Movie> movies = admin.getMovies();
        if (movies.isEmpty()) {
            System.out.println("No movies available.");
        } else {
            for (int i = 0; i < movies.size(); i++) {
                Movie movie = movies.get(i);
                System.out.println((i + 1) + "." + movie.getTitle() + " (" + movie.getShowtime() + ")");
            }
        }
    }

    private static void bookMovie(Scanner scanner, User user) {
        viewMovies();
        System.out.print("Enter movie number to book: ");
        int movieNumber = scanner.nextInt();
        scanner.nextLine();
        if (movieNumber < 1 || movieNumber > admin.getMovies().size()) {
            System.out.println("Invalid movie number.");
            return;
        }
        Movie movie = admin.getMovies().get(movieNumber - 1);
        System.out.print("Enter seat number: ");
        String seat = scanner.nextLine();
        Booking booking = new Booking(movie, seat);
        user.addBooking(booking);
        System.out.println("Movie booked successfully.");
    }

    private static void viewBookingHistory(User user) {
        List<Booking> bookings = user.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No booking history.");
        } else {
            for (Booking booking : bookings) {
                System.out.println("Movie: " + booking.getMovie().getTitle() + ", Showtime: " + booking.getMovie().getShowtime() + ", Seat: " + booking.getSeat());
            }
        }
    }
}

class User {
    private String username;
    private String password;
    private List<Booking> bookings;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.bookings = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}

class Movie {
    private String title;
    private String showtime;

    public Movie(String title, String showtime) {
        this.title = title;
        this.showtime = showtime;
    }

    public String getTitle() {
        return title;
    }

    public String getShowtime() {
        return showtime;
    }
}

class Booking {
    private Movie movie;
    private String seat;

    public Booking(Movie movie, String seat) {
        this.movie = movie;
        this.seat = seat;
    }

    public Movie getMovie() {
        return movie;
    }

    public String getSeat() {
        return seat;
    }
}

class Admin {
    private List<Movie> movies;

    public Admin() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        this.movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }
}