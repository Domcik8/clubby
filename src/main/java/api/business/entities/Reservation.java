package api.business.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Optional;

@Entity
@Table(name = "reservations", schema = "main", catalog = "clubby")
public class Reservation {
    private int reservationid;
    private Cottage cottage;
    private User user;
    private Payment payment;
    private Date dateFrom;
    private Date dateTo;
    private boolean cancelled;
    private Date created;

    @Id
    @Column(name = "reservationid", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getReservationid() {
        return reservationid;
    }

    public void setReservationid(int reservationid) {
        this.reservationid = reservationid;
    }

    @Basic
    @Column(name = "datefrom")
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Basic
    @Column(name = "dateto")
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Basic
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Basic
    @Column(name = "cancelled")
    public boolean getCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        return reservationid == that.reservationid;
    }

    @Override
    public int hashCode() {
        return reservationid;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", referencedColumnName = "id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "cottageid", referencedColumnName = "id")
    public Cottage getCottage() {
        return cottage;
    }

    public void setCottage(Cottage cottage) {
        this.cottage = cottage;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "paymentid", referencedColumnName = "paymentid")
    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Transient
    public int getStatus() {
        Optional<MoneyTransaction> transaction = getPayment().getTransactions().stream().filter(t -> t.getStatus() == api.contracts.enums.TransactionStatus.approved.getValue()).findFirst();
        int status;
        if (transaction.isPresent()) {
            status = transaction.get().getStatus();
        } else {
            status = api.contracts.enums.TransactionStatus.pending.getValue();
        }

        return status;
    }
}
