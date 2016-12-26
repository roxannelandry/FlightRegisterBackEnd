Feature: Register baggage

@niveau=medium @strategy=application
Scenario: passenger register a second baggage 
 Given a passenger Bob who is register on flight AC-1234 in economy class
 And  Bob already has a checked baggage that respect rules
 When registering this second baggage:
   | type    | poids | taille |
   | CHECKED | 20kg  | 100cm  |
 Then the total cost is 50$ 