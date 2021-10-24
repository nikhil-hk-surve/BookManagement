# BookManagement
####This Api allows to add, modify, read and delete Books and their Types.
####It also allows to Calculate Total Amount, Discounted Amount and Total Discount Percentage on the books added.


##Book Operations
##1. getBook
  #### Url: http://localhost:8080/rest/book/{id}
  ####Input: id
  ####Result: Book object
  ####Desc: Takes id as param and returns Book

##2. getAllBook
#### Url: http://localhost:8080/rest/book/all
####Input: NA
####Result: List of Book
####Desc: returns list of Books

##3. Save
#### Url: http://localhost:8080/rest/book/save
#### Input: Book
####Result: BookDto object 
####Desc: Takes Book in request and returns BookDto


##4. Delete 
#### Url: http://localhost:8080/rest/book/delete/{id}
#### Input: id
####Result: NA
####Desc: Takes Book in request and deletes from Book table

##5. DeleteAll
#### Url: http://localhost:8080/rest/book/delete/all
#### Input: NA
####Result: NA
####Desc: deletes all the books from Book table

##6. checkout
##### Url: http://localhost:8080/rest/book/checkout
##### Input: NA
#####Result: returns checkout DTO object
#####Desc: returns DTO with total Amount, Discounted amount and total discount percentage for all the Books according to discount applied on each Type of books.


##Type/Classification Operations

##1. getType
#### Url: http://localhost:8080/rest/type/{id}
####Input: id
####Result: Type object
####Desc: Takes id as param and returns Type

##2. getAllType
#### Url: http://localhost:8080/rest/type/all
####Input: NA
####Result: List of Type
####Desc: returns list of Types

##3. Save
#### Url: http://localhost:8080/rest/type/save
#### Input: Type
####Result: Type object
####Desc: Takes Type in request and returns Type


##4. Delete
#### Url: http://localhost:8080/rest/type/delete/{id}
#### Input: id
####Result: NA
####Desc: Takes Type in request and deletes from Type table

##5. DeleteAll
#### Url: http://localhost:8080/rest/type/delete/all
#### Input: NA
####Result: NA
####Desc: deletes all the types from Type table

##Docker Build Command
###docker build -t springio/gs-spring-boot-docker.