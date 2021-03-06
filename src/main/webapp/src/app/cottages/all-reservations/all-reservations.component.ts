import {Component} from 'angular2/core';
import {Observable} from 'rxjs/Observable';

import {ReservationList} from "../shared/reservation-list/reservation-list.component";
import {ReservationService} from "../shared/reservation.service";
import {ReservationListItem} from "../shared/reservation-list-item.model";
import {UserService} from "../../shared/user.service";

@Component({
    selector: 'all-reservations',
    template: require('./all-reservations.component.html'),
    styleUrls: [require('./all-reservations.component.scss')],
    providers: [ReservationService, UserService],
    directives: [ReservationList],
    pipes: []
})
export class AllReservations {
    reservations: [ReservationListItem];

    constructor(private reservationService: ReservationService) {
        reservationService.getReservations('all')
            .subscribe(
                data => this.reservations = data.reservations,
                error => this.handleError(error)
            );
    }

    private handleError(error: any) {
        // In a real world app, we might send the error to remote logging infrastructure
        let errMsg = error.message || 'Server error';
        console.error(errMsg); // log to console instead
        return Observable.throw(errMsg);
    }
}
