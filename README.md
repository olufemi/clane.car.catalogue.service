# clane.car.catalogue.service
CLANE JAVA SOFTWARE ENGINEER TASK
This project manages Car management i.e. To create Car Catalogue with dependency on Categories of Cars.

I. The Table Categories manage the Categories. For every Car Catalogue to be created, a categoryId is required. It is expected that consumer will
   pull the Car Categories returned data through its API to get the created Categories Ids. 

II. The Car Tags Ids is also be pulled from Tag table, the tag Id is optional during creation.
III. The Car Images which is optional is expected in Base64

The following parameters are expected to create a Car Catalogue;
1. name – required
2. description – required
3. category – required
4. tags – optional
5. images - optional
recordTimestamps – (this is created by the application)
engine number (it is sequential and 5 digits long, it will be created by the app)

The Search API
Car Catague(s) can be serached using a prefered cateria (name, description, category/categoryId or tag/tagId)
, such as choosen any of the following or combining them, two APIs are shared in the Postman collection, whth
as based-api-url: http://localhost:8030/api/search-car
