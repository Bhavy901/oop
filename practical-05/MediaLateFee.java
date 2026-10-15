public class MediaLateFee {

    public abstract static class Media {
        private final String title;
        private final int daysLate;

        public Media(String title, int daysLate) {
            this.title = title;
            this.daysLate = Math.max(0, daysLate);
        }

        public String getTitle() {
            return title;
        }

        public int getDaysLate() {
            return daysLate;
        }

        public abstract double computeLateFee();
    }

    public static class Book extends Media {
        public Book(String title, int daysLate) {
            super(title, daysLate);
        }

        @Override
        public double computeLateFee() {
            return getDaysLate() * 2.0;
        }
    }

    public static class DVD extends Media {
        public DVD(String title, int daysLate) {
            super(title, daysLate);
        }

        @Override
        public double computeLateFee() {
            if (getDaysLate() == 0) return 0;
            double fee = getDaysLate() * 5.0;
            if (getDaysLate() > 3) {
                fee += 20.0;
            }
            return fee;
        }
    }

    public static class VideoGame extends Media {
        public VideoGame(String title, int daysLate) {
            super(title, daysLate);
        }

        @Override
        public double computeLateFee() {
            return Math.min(200.0, getDaysLate() * 15.0);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Library Returned Media Late Fee Calculation ===");

        Media[] returnedBatch = {
            new Book("Clean Code", 4),
            new DVD("Interstellar", 5),
            new VideoGame("The Legend of Zelda", 10),
            new Book("Java: The Complete Reference", 0),
            new DVD("Inception", 2),
            new VideoGame("Cyberpunk 2077", 25)
        };

        double totalBatchFees = 0;

        for (Media item : returnedBatch) {
            double fee = item.computeLateFee();
            totalBatchFees += fee;

            System.out.printf("%-12s | Title: %-30s | Overdue: %2d days | Late Fee: Rs. %6.2f%n",
                    item.getClass().getSimpleName(),
                    "\"" + item.getTitle() + "\"",
                    item.getDaysLate(),
                    fee);
        }

        System.out.printf("%n======================================================%n");
        System.out.printf("Total Overdue Fees for Returned Batch: Rs. %.2f%n", totalBatchFees);
    }
}
