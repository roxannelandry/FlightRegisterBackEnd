Feature: Assign seat

@niveau=large @happypath @risque @strategy=ui
Scenario: random seat assignation
Given a passenger Alice that have a reservation in a flight
And that the flight contains available seats
And that her checkin has been done
When she ask for random seat assignation
Then her seat is assigned
