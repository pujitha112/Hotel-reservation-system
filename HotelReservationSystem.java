import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Room {
    private int roomId;
    private String roomType;
    private double price;
    private boolean isAvailable;

    public Room(int roomId, String roomType, double price, boolean isAvailable) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    // Getters and setters
    public int getRoomId() { return roomId; }
    public String getRoomType() { return roomType; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean isAvailable) { this.isAvailable = isAvailable; }

    @Override
    public String toString() {
        return "Room ID: " + roomId + ", Type: " + roomType + ", Price: " + price + ", Available: " + isAvailable;
    }
}

class User {
    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    @Override
    public String toString() {
        return "User ID: " + userId + ", Username: " + username;
    }
}

class Reservation {
    private int reservationId;
    private User user;
    private Room room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(int reservationId, User user, Room room, Date checkInDate, Date checkOutDate) {
        this.reservationId = reservationId;
        this.user = user;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    // Getters and setters
    public int getReservationId() { return reservationId; }
    public User getUser() { return user; }
    public Room getRoom() { return room; }
    public Date getCheckInDate() { return checkInDate; }
    public Date getCheckOutDate() { return checkOutDate; }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId + ", Room: " + room + ", User: " + user + 
               ", Check-in: " + checkInDate + ", Check-out: " + checkOutDate;
    }
}

class PaymentProcessing {

    public boolean processPayment(User user, double amount) {
        // Simulate payment processing
        System.out.println("Processing payment for " + user.getUsername() + ": $" + amount);
        return true; // Assume payment is always successful
    }
}

public class HotelReservationSystem {
    private List<Room> rooms;
    private List<User> users;
    private List<Reservation> reservations;

    public HotelReservationSystem() {
        rooms = new ArrayList<>();
        users = new ArrayList<>();
        reservations = new ArrayList<>();
    }

    // Method to add rooms
    public void addRoom(Room room) {
        rooms.add(room);
    }

    // Method to register users
    public void registerUser(User user) {
        users.add(user);
    }

    // Method to search for available rooms
    public List<Room> searchAvailableRooms(String roomType) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getRoomType().equals(roomType)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    // Method to make a reservation
    public boolean makeReservation(User user, Room room, Date checkIn, Date checkOut, PaymentProcessing paymentProcessing) {
        if (room.isAvailable()) {
            double totalPrice = room.getPrice() * (checkOut.getTime() - checkIn.getTime()) / (1000 * 60 * 60 * 24);
            if (paymentProcessing.processPayment(user, totalPrice)) {
                room.setAvailable(false);
                Reservation reservation = new Reservation(reservations.size() + 1, user, room, checkIn, checkOut);
                reservations.add(reservation);
                System.out.println("Reservation successful! " + reservation);
                return true;
            }
        }
        return false;
    }

    // Method to view user reservations
    public List<Reservation> viewReservations(User user) {
        List<Reservation> userReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getUser().equals(user)) {
                userReservations.add(reservation);
            }
        }
        return userReservations;
    }

    public static void main(String[] args) {
        HotelReservationSystem system = new HotelReservationSystem();

        // Adding rooms
        system.addRoom(new Room(1, "Single", 100, true));
        system.addRoom(new Room(2, "Double", 150, true));
        system.addRoom(new Room(3, "Suite", 300, true));

        // Registering a user
        User user = new User(1, "john_doe", "password123");
        system.registerUser(user);

        // Searching for available rooms
        List<Room> availableRooms = system.searchAvailableRooms("Single");
        System.out.println("Available rooms: " + availableRooms);

        // Making a reservation
        PaymentProcessing paymentProcessing = new PaymentProcessing();
        boolean success = system.makeReservation(user, availableRooms.get(0), new Date(), new Date(System.currentTimeMillis() + 86400000), paymentProcessing);

        // Viewing reservations
        if (success) {
            List<Reservation> reservations = system.viewReservations(user);
            System.out.println("Your reservations: " + reservations);
        }
    }
}
