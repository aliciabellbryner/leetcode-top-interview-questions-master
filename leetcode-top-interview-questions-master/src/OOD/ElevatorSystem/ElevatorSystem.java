package OOD.ElevatorSystem;

import java.util.*;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.logging.Logger;

public class ElevatorSystem {
    /*
    //https://github.com/seahrh/liftoo
    public class Solution1 {
        //Lift.java
        public class Lift {
            private static final Logger log = LoggerFactory.getLogger(Lift.class);

            public class Defaults {
                private static final int MIN_FLOOR = 1;
                private static final int MAX_FLOOR = 20;
                private static final int MAX_LOAD = 2000;
            }

            private int minFloor = Defaults.MIN_FLOOR;
            private int maxFloor = Defaults.MAX_FLOOR;
            private int floor = minFloor;
            private int load = 0;
            private int maxLoad = Defaults.MAX_LOAD;
            private State state = State.STAND;
            private boolean isDoorOpen = false;
            private String id = null;
            private Queue<Integer> upHeap = new PriorityQueue<>(maxFloor);//用pq的目的是为了可以优先找到同方向最近的楼层
            private Queue<Integer> downHeap = new PriorityQueue<>(maxFloor, Collections.reverseOrder());

            private enum State {
                MAINTENANCE, STAND, UP, DOWN
            }

            private Lift(String id, int maxFloor, int minFloor, int maxLoad) {
                this(id, maxFloor, minFloor);
                maxLoad(maxLoad);
            }

            private Lift(String id, int maxFloor, int minFloor) {
                this(id, maxFloor);
                minFloor(minFloor);
                floor(minFloor);
            }

            private Lift(String id, int maxFloor) {
                id(id);
                maxFloor(maxFloor);
            }

            public static Lift get(String id, int maxFloor) {
                return new Lift(id, maxFloor);
            }

            public static Lift get(String id, int minFloor, int maxFloor) {
                return new Lift(id, maxFloor, minFloor);
            }

            public static Lift getLiftWithMaxLoad(String id, int maxFloor, int maxLoad) {
                return new Lift(id, maxFloor, Defaults.MIN_FLOOR, maxLoad);
            }

            public static Lift get(String id, int minFloor, int maxFloor, int maxLoad) {
                return new Lift(id, maxFloor, minFloor, maxLoad);
            }

            protected boolean isUpHeapEmpty() {
                return upHeap.isEmpty();
            }

            protected void enqueueFloorUpward(int floor) {
                if (upHeap.contains(floor)) {
                    return;
                }
                upHeap.add(floor);
            }

            protected void dequeueFloorUpward() {
                upHeap.remove();
            }

            protected boolean isDownHeapEmpty() {
                return downHeap.isEmpty();
            }

            protected void enqueueFloorDownward(int floor) {
                if (downHeap.contains(floor)) {
                    return;
                }
                downHeap.add(floor);
            }

            protected void dequeueFloorDownward() {
                downHeap.remove();
            }

            protected int minFloor() {
                return minFloor;
            }

            protected void minFloor(int minFloor) {
                if (minFloor < 1) {
                    log.error("minFloor must be greater than or equal to 1");
                    throw new IllegalArgumentException();
                }
                if (minFloor > maxFloor) {
                    log.error("minFloor must be less than or equal to maxFloor");
                    throw new IllegalArgumentException();
                }
                this.minFloor = minFloor;
            }

            protected int maxFloor() {
                return maxFloor;
            }

            protected void maxFloor(int maxFloor) {
                if (maxFloor < 1) {
                    log.error("maxFloor must be greater than or equal to 1");
                    throw new IllegalArgumentException();
                }
                if (maxFloor < minFloor) {
                    log.error("maxFloor must be greater than or equal to minFloor");
                    throw new IllegalArgumentException();
                }
                this.maxFloor = maxFloor;
            }

            protected int floor() {
                return floor;
            }

            protected void floor(int floor) {
                if (floor < minFloor) {
                    log.error(
                            "current floor must not be less than minFloor\nfloor [{}]\nminFloor [{}]",
                            floor, minFloor);
                    throw new IllegalArgumentException();
                }
                if (floor > maxFloor) {
                    log.error(
                            "current floor must not be greater than maxFloor\nfloor [{}]\nmaxFloor [{}]",
                            floor, maxFloor);
                    throw new IllegalArgumentException();
                }
                this.floor = floor;
            }

            protected int load() {
                return load;
            }

            protected void load(int load) {
                if (load < 0) {
                    log.error("load must be greater than or equal to 0");
                    throw new IllegalArgumentException();
                }
                this.load = load;
            }

            protected int maxLoad() {
                return maxLoad;
            }

            protected void maxLoad(int maxLoad) {
                if (maxLoad < 1000) {
                    log.error("maxLoad must be greater than or equal to 1000 kg");
                    throw new IllegalArgumentException();
                }
                this.maxLoad = maxLoad;
            }

            protected boolean isMaintenanceState() {
                return state.equals(State.MAINTENANCE);
            }

            protected void setMaintenanceState() {
                state = State.MAINTENANCE;
            }

            protected boolean isStandState() {
                return state.equals(State.STAND);
            }

            protected void setStandState() {
                state = State.STAND;
            }

            protected boolean isUpState() {
                return state.equals(State.UP);
            }

            protected void setUpState() {
                state = State.UP;
            }

            protected boolean isDownState() {
                return state.equals(State.DOWN);
            }

            protected void setDownState() {
                state = State.DOWN;
            }

            protected boolean isDoorOpen() {
                return isDoorOpen;
            }

            protected void openDoor() {
                isDoorOpen = true;
            }

            protected void closeDoor() {
                isDoorOpen = false;
            }

            protected String id() {
                return id;
            }

            private void id(String id) {
                if (id == null || id.isEmpty()) {
                    log.error("id must not be null or empty string");
                    throw new IllegalArgumentException();
                }
                this.id = id;
            }

        }

        //CloseDoorCommand.java
        public class CloseDoorCommand extends CommandBase {

            public CloseDoorCommand(Lift lift, long time, boolean isExternal) {
                super(lift, time, isExternal);
            }

            public CloseDoorCommand(Lift lift, long time) {
                super(lift, time);
            }

            protected void doIt() {
                Lift lift = lift();
                if (lift.isDoorOpen()) {
                    lift.closeDoor();
                }
            }
        }

        //CommandBase.java
        public abstract class CommandBase {
//            private static final Logger log = LoggerFactory.getLogger(CommandBase.class);
            private Lift lift = null;
            private long time = 0;
            private boolean isExternal = false;

            public CommandBase(Lift lift, long time, boolean isExternal) {
                this(lift, time);
                isExternal(isExternal);
            }

            public CommandBase(Lift lift, long time) {
                lift(lift);
                time(time);
            }

            public void execute() {
                if (isIllegalOperation()) {
//                    log.warn("Illegal operation: command aborted");
                    return;
                }
                doIt();
            }

            abstract protected void doIt();

            public Lift lift() {
                return lift;
            }

            public void lift(Lift lift) {
                if (lift == null) {
//                    log.error("lift must not be null");
                    throw new IllegalArgumentException();
                }
                this.lift = lift;
            }

            public long time() {
                return time;
            }

            public void time(long time) {
                if (time < 0) {
//                    log.error("time must be greater than or equal to zero");
                    throw new IllegalArgumentException();
                }
                this.time = time;
            }

            public boolean isExternal() {
                return isExternal;
            }

            public void isExternal(boolean isExternal) {
                this.isExternal = isExternal;
            }

            private boolean isIllegalOperation() {
                if (isExternal && lift.isMaintenanceState()) {
//                    log.info("A lift does not accept external commands in MAINTENANCE state.");
                    return true;
                }
                return false;
            }

        }

        //GoToFloorCommand.java
        public class GoToFloorCommand extends CommandBase {
//            private static final Logger log = LoggerFactory.getLogger(GoToFloorCommand.class);
            private int floor = 1;

            public GoToFloorCommand(Lift lift, long time, boolean isExternal, int floor) {
                super(lift, time, isExternal);
                floor(floor);
            }

            public GoToFloorCommand(Lift lift, long time, int floor) {
                super(lift, time);
                floor(floor);
            }

            protected void doIt() {
                Lift lift = lift();
                int curr = lift.floor();
                if (floor == curr) {
                    log.warn("Invalid GoToFloorCommand: Floor request is the same as current floor. Issue OpenDoorCommand instead.");
                    return;
                }
                // Upper floor
                if (floor > curr) {
                    lift.enqueueFloorUpward(curr);
                    return;
                }
                // Lower floor
                lift.enqueueFloorDownward(curr);
            }

            public int floor() {
                return floor;
            }

            private void floor(int floor) {
                if (floor < 1) {
//                    log.error("floor must be greater than or equal to 1");
                    throw new IllegalArgumentException();
                }
                this.floor = floor;
            }
        }

        //OpenDoorCommand.java
        public class OpenDoorCommand extends CommandBase {

            public OpenDoorCommand(Lift lift, long time, boolean isExternal) {
                super(lift, time, isExternal);
            }

            public OpenDoorCommand(Lift lift, long time) {
                super(lift, time);
            }

            protected void doIt() {
                Lift lift = lift();
                if (!lift.isDoorOpen()) {
                    lift.openDoor();
                }
            }
        }

    }



     */





    //https://www.lintcode.com/problem/708/solution/17191
    public class Solution2 {
        enum Direction {
            UP, DOWN
        }

        enum Status {
            UP, DOWN, IDLE
        }

        class Request {
            private int level;

            public Request(int l)
            {
                level = l;
            }

            public int getLevel()
            {
                return level;
            }
        }

        class ElevatorButton {
            private int level;
            private Elevator elevator;

            public ElevatorButton(int level, Elevator e)
            {
                this.level = level;
                this.elevator = e;
            }

            public void pressButton()
            {
                InternalRequest request = new InternalRequest(level);
                elevator.handleInternalRequest(request);
            }
        }

        class ExternalRequest extends Request{

            private Direction direction;

            public ExternalRequest(int l, Direction d) {
                super(l);
                // TODO Auto-generated constructor stub
                this.direction = d;
            }

            public Direction getDirection()
            {
                return direction;
            }
        }

        class InternalRequest extends Request{

            public InternalRequest(int l) {
                super(l);
                // TODO Auto-generated constructor stub
            }
        }

        public class Elevator {

            private List<ElevatorButton> buttons;

            private List<Boolean> upStops;
            private List<Boolean> downStops;

            private int currLevel;
            private Status status;

            public Elevator(int n)
            {
                buttons = new ArrayList<ElevatorButton>();
                upStops = new ArrayList<Boolean>();
                downStops = new ArrayList<Boolean>();
                currLevel = 0;
                status = Status.IDLE;

                for(int i = 0; i < n; i++)
                {
                    upStops.add(false);
                    downStops.add(false);
                }
            }

            public void insertButton(ElevatorButton eb)
            {
                buttons.add(eb);
            }

            public void handleExternalRequest(ExternalRequest r)
            {
                // Write your code here
            }

            public void handleInternalRequest(InternalRequest r)
            {
                // Write your code here
            }

            public void openGate() throws Exception
            {
                // Write your code here
            }

            public void closeGate()
            {
                // Write your code here
            }

            private boolean noRequests(List<Boolean> stops)
            {
                for(int i = 0; i < stops.size(); i++)
                {
                    if(stops.get(i))
                    {
                        return false;
                    }
                }
                return true;
            }

            public String elevatorStatusDescription()
            {
                String description = "Currently elevator status is : " + status
                        + ".\nCurrent level is at: " + (currLevel + 1)
                        + ".\nup stop list looks like: " + upStops
                        + ".\ndown stop list looks like:  " + downStops
                        + ".\n*****************************************\n";
                return description;
            }
        }
    }

}
