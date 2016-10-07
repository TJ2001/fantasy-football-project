# Fantasy Football Calculator

#### _This application will enable fantasy football enthusiasts to calculate the optimal draft according to players or teams left on the board. There is a built in algorithm to help provide assistance to find the best player according to past statistics. {October 7, 2016}_

#### _**Software Architect: Tim Herold, Lead Art Director: Sheena Nickerson, Lead Technical Director: Yusuf Qedan, and Project Manager: Tim Jung**_

## Description

_The purpose of this application is to work with database and utilize algorithms to find meaningful and useful ways to use the data provided. We hope this project will provide a good proof of concept and lay groundwork for future projects in working with other databases assisting users and interested parties._

## Setup/Installation Requirements

* Download zip or clone files from Github to desktop.
* Have Gradle and Postgres installed on the system properly.
* In a terminal have Postgres run in the background by using command: "Postgres".
* In another tab or window run PSQL.
* In PSQL: (1) "CREATE DATABASE fantasy_football;" (2) In Bash: "psql fantasy_football < fantasy_football.sql".
* Open console to the project package folder and run "gradle run" .

Everything should just work fine, if directions are followed.

## Specifications

|Behavior|Input|Output|
|---|---|---|
|User will call method for bestQb | Database of QB stats  | Rankings of best QBs according to algorithm |
|User will call method for bestWR | Database of WR stats  | Rankings of best WRs according to algorithm |
|User will call method for bestRB | Database of RB stats  | Rankings of best RBs according to algorithm |
|User will call method for bestTE | Database of TE stats  | Rankings of best TEs according to algorithm |
|User will call method for bestK | Database of K stats  | Rankings of best Ks according to algorithm |
|User will call method for bestTeamDefense | Database of TeamDefense stats  | Rankings of best TeamDefenses according to algorithm |
|There will be a final algorithm that will sort the best players and teams using a proven draft order done by professional fantasy football theoreticians.  | Stats for all positions and teams. | Ranking of draft order. |

## Known Bugs

_It's mostly bug free. Older browsers might have issues._

## Support and contact details

_Feel free to contact any of the the contributors to the project by Github or snail-mail via Epicodus._

## Technologies Used

_Java, Gradle, Junit, Spark, Velocity, Bootstrap, CSS, HTML, PostgreSQL, sql2o, JavaScript, JQuery_

### License

*This software is licensed under the MIT license*

CC Copyright (c) 2016
