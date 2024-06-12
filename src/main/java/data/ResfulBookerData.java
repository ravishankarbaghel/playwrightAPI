package data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResfulBookerData {

    /**
     * {
     * "firstname": "Sally",
     * "lastname": "Brown",
     * "totalprice": 111,
     * "depositpaid": true,
     * "bookingdates": {
     * "checkin": "2013-02-23",
     * "checkout": "2014-10-23"
     * },
     * "additionalneeds": "Breakfast"
     * }
     */

    private String firstname;
    private String lastname;
    private String totalprice;
    private String depositpaid;
    private RestfullBooker_BookingDates bookingdates;
    private String additionalneeds;
    private String bookingid;
}
