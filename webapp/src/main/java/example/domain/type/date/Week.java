package example.domain.type.date;

/**
 * 週
 */
public class Week {
    Date sunday;
    Date monday;
    Date tuesday;
    Date wednesday;
    Date thursday;
    Date friday;
    Date saturday;

    public Week(Date sunday, Date monday, Date tuesday, Date wednesday, Date thursday, Date friday, Date saturday) {
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
    }
}
