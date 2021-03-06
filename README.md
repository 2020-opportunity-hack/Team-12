
## Team-12

## About NPO - Sunday Friends Foundation
- [About Sunday Friends Foundation](https://www.sundayfriends.org)
- [NPO Slack](https://opportunity-hack.slack.com/archives/C01CUAMHHKM)

## Introduction
Learn & Earn ticket system of the Sunday Friends foundation is a key component of financial literacy program for youth and adults. Learning to make good financial decisions is one of the core competencies of Sunday Friends’ mission to break the generational cycle of poverty. We are building a mobile solution for ticket management system.
This solution for the ticket system would empower the family members as they develop financial management skills by reducing their dependency on others to access their ticket accounts. 

## Quick Preview
1. [Quick 4 mins demo](https://youtu.be/1rxchsODm7Y)
2. [Complete System walkthrough](https://youtu.be/sgxF_pHiOtM)
3. [User's documentation here](https://github.com/2020-opportunity-hack/Team-12/blob/main/Users_Flow.pdf)
4. [User's demo video](https://youtu.be/nG2zKhh7eaI)
5. [Admin's documentation here](https://github.com/2020-opportunity-hack/Team-12/blob/main/Admin_Flow.pdf)
6. [Admin's demo video](https://youtu.be/aV69WUlLijU)

## Technologies Used 
Purpose | Technologies
--- | ---
Prototyping mobile app screens | Figma
Front end of mobile apps | Android (Java), iOS (Swift)
REST API | Spring, Core Java
Database | MySQL
Deployment | AWS EC2 for Rest Api & AWS RDS for Database



## Overall Architecture
![alt text](https://github.com/2020-opportunity-hack/Team-12/blob/main/architecture.jpg?raw=true)

## Database Schema
![alt text](https://github.com/2020-opportunity-hack/Team-12/blob/main/entitydiag.jpg?raw=true)

## REST APIs
![alt text](https://github.com/2020-opportunity-hack/Team-12/blob/main/API2.jpg?raw=true)

## Challenges
1. The primary challenge had been coordinating different tasks in the current virtual environment. 
2. Technical challenges were in keeping it a minimum viable product and not to involve too many dependencies. For eg. sign up for a member whose family is already registered. We simplified this by exposing the familyId after registration. So the next family member if wants to register needs to know the familyId.
3. Solved authentication/security challanges by using the standard Google Sign in and then onboarding user on our platform after the user is autneticated with Google.

## Future Enhancements
1. 2% Interest - Add a cron job that runs on the last day of the month and deposit 2% interest in the user's account.
2. Personalize the dashboard by adding things like interests earned, balance, spend analytics, etc.
3. Add shopping experience for user where they can shop daily essentials on the go with their mobile app.
4. Add shopping platform for admin so that they can upload products and manager the shopping store.
5. Extend the experience to web as well, atleast for admin so that they can manage all the users easily. 
6. Add a feature as easy sign up for a new family member through a link particular to a family
7. Add filters to filter out the transaction history
8. Add search for admin so that admin can easily search for a member and do actions related to that user.

## Contributers
- [Abhinav Roy](https://devpost.com/abhinroy)
- [Evan Li](https://devpost.com/coolbeans25/)
- [Harshali Talele](https://devpost.com/harshalitalele)
- [Manas Mahapatra](https://devpost.com/manasm190293)
- [DI Chang](https://devpost.com/dchang136)

## Contact
- [Team Slack Channel](https://opportunity-hack.slack.com/archives/C01FL4AHQKS)
