package example.domain.model.contract.hours;

/**
 * 労働時間区分
 */
public enum WorkingTimeType {
    所定労働,
    法定時間内残業,
    法定時間外残業,
    所定休日労働,
    法定休日労働,
    月60時間超えの時間外労働;
}
