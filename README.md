# BookManagement
####This Api allows to add, modify, read and delete Books and their Types.
####It also allows to Calculate Total Amount, Discounted Amount and Total Discount Percentage on the books added.

----------
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
#####Desc: Calculates default discount and returns DTO with total Amount, Discounted amount and total discount percentage for all the Books according to discount applied on each Type of books.


##6. checkoutList with Promo Code
##### Url: http://localhost:8080/rest/book/checkoutList/promotion/{code}
##### Input: List of Books and Promo code (optional)
#####Result: returns checkout DTO object
#####Desc: Calculate discount as per the promo or default discount for each book and returns DTO with total Amount, Discounted amount and total discount percentage for all the Books according to discount applied on each Type of books.



----------------------------
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

-------------------
##Docker Build Command
###docker build -f Dockerfile -t bookmanagement
###docker run -p 8080:8080 bookmanagement

------------------------
##DataBase details
###Url: http://localhost:8080/h2
###User Name: sa
###Password: sa
###Jdbc Url: jdbc:h2:mem:books
--------------------
##Sample JSONs
###Book
{
"id": 2,
"name": "Harry Potter 2",
"description": "Childrens book",
"author": "Swanik",
"type":       {
"type_id": 2,
"typeName": "friction",
"discount": 25
},
"price": 200,
"isbn": "1234"
}

###List of Books
[ {
"id": 2,
"name": "Harry Potter 2",
"description": "Childrens book",
"author": "Swanik",
"type":       {
"type_id": 2,
"typeName": "friction",
"discount": 25
},
"price": 200,
"isbn": "1234"
},
{
"id": 3,
"name": "java",
"description": "Childrens book",
"author": "Swanik",
"type":       {
"type_id": 3,
"typeName": "education",
"discount": 50
},
"price": 500,
"isbn": "1234"
}
]

###Type
"type":       {
"type_id": 2,
"typeName": "friction",
"discount": 25
}

###Checkout
{
"totalAmount": 1000,
"discountedPrice": 700,
"totalDiscount": 30,
"error": null
}

####Note: totalDiscount is Discount Percentage on final amount.