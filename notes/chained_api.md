# What is chained API call ?

suppose you have to delete a user with username "bekar_user"
 
and there is two APIs
Get user by username .. which takes username as input and returns all user details like user id , email , name etc
and Delete user by userID ::: which takes userID as input and deteltes the user
 
 
and you know only username not user id so you can't directly use delete API
 
- so first you will have to call get user by username
- then extract user id from response
- then use this user id to make api call to delete user
- then again use fist API to check if user is actually deleted from backend or not
 
 
 
so here next API call depends on response of first API call
 
so it is called chained API call
 