public class RemoteControl {

    public interface Switchable {
        void on();
        void off();
        boolean isOn();
        String getDeviceName();

        default void toggle() {
            if (isOn()) {
                off();
            } else {
                on();
            }
        }
    }

    public static class Light implements Switchable {
        private boolean state = false;

        @Override
        public void on() {
            state = true;
            System.out.println("Light turned ON.");
        }

        @Override
        public void off() {
            state = false;
            System.out.println("Light turned OFF.");
        }

        @Override
        public boolean isOn() {
            return state;
        }

        @Override
        public String getDeviceName() {
            return "Living Room Light";
        }
    }

    public static class Fan implements Switchable {
        private boolean state = false;

        @Override
        public void on() {
            state = true;
            System.out.println("Fan turned ON (spinning).");
        }

        @Override
        public void off() {
            state = false;
            System.out.println("Fan turned OFF.");
        }

        @Override
        public boolean isOn() {
            return state;
        }

        @Override
        public String getDeviceName() {
            return "Ceiling Fan";
        }
    }

    @FunctionalInterface
    public interface SchedulePolicy {
        boolean canSwitchOn(Switchable device, int hour);
    }

    public static void main(String[] args) {
        System.out.println("=== Remote Control Interface Simulation ===");

        Switchable[] devices = { new Light(), new Fan() };

        System.out.println("\n--- Toggling Devices ---");
        for (Switchable device : devices) {
            System.out.println("Toggling " + device.getDeviceName() + ":");
            device.toggle();
            device.toggle();
        }

        System.out.println("\n--- Policy Evaluation ---");
        int currentHour = 23;

        SchedulePolicy nightTimePolicyAnonymous = new SchedulePolicy() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                if (device instanceof Fan && (hour >= 22 || hour < 6)) {
                    return false;
                }
                return true;
            }
        };

        SchedulePolicy nightTimePolicyLambda = (device, hour) -> {
            if (device instanceof Light && (hour < 6 || hour > 23)) {
                return false;
            }
            return true;
        };

        for (Switchable device : devices) {
            boolean allowedAnon = nightTimePolicyAnonymous.canSwitchOn(device, currentHour);
            boolean allowedLambda = nightTimePolicyLambda.canSwitchOn(device, currentHour);

            System.out.println(device.getDeviceName() + " at hour " + currentHour + ":");
            System.out.println("  - Fan Restriction Policy (Anonymous Class): Allowed? " + allowedAnon);
            System.out.println("  - General Light Policy (Lambda Expression): Allowed? " + allowedLambda);
        }
    }
}
