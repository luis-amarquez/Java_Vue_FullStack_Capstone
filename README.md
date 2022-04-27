## DevByte (Developer Social Network)
DevBytes is a social networking site similar to twitter. It offers multiples types of posts rather than just a single
type. You are able to create a post to express your thoughts, ask question, crack some jokes, share a code snippet, or
even an article.

## Contents
- [Daily Progress](#daily-progress)
- [User Stories](#user-stories)
  - [AppUser](#appuser)
  - [Posts](#post)
- [My Journey](#journey)
- [Software Architecture](#software-architecture)
- [Database Schema](#db-schema)

## Daily Progress
![](https://progress-bar.dev/92?title=Completed&width=100)
- April 26th
  - Added message view in the frontend that allows one to view a list of all
  message rooms.
  - Added chat view in the frontend that displays all the messages in a message
  room and allows you to add a new message.
  added the frontend code to allow for user to register.

![](https://progress-bar.dev/90?title=Completed&width=100)
- April 25th
  - Implemented the service files.
  - Created the controller for `Message` and `MessageRoom`
  - Fixed bug when adding a new comment that would sometimes result in a 400

![](https://progress-bar.dev/85?title=Completed&width=100)
- April 22nd
  - Completed the integration of post methods in the front end.
    - Able to create new post and comment.
    - Able to delete account from profile page.
    - Able to delete posts and comments.
  - Added automatic jwt `accessToken` refresh with `refreshToken` if `accessToken` request results in a 403
  - Created 2 additional models
    - `MessageRoom`
    - `Message`
    - Will be used for messaging
  - Created repositories and service only files for models


![](https://progress-bar.dev/35?title=Completed&width=100)
- April 15th
  - Initialized the Vue project to be used for the frontend.
  - Created the base layout that the site will use.
  - Designed the view that the home page and explore page will use.
  - Designed the component which is for a user post that contains the desired information

![](https://progress-bar.dev/25?title=Completed&width=100)
- April 14th
    - Created the authentication required mappings.
      - Hard coded getting auth user from a specific username until security is implemented.
    - Created Dtos to handle creating new post and comment.
    - Created Dto to ensure pagination parameters are valid & reduce repetitive code
    - Added mock data to the database.
    - Tested endpoints using postman

![](https://progress-bar.dev/20?title=Completed&width=100)
- April 13th
  - Setup MariaDB config in `application.properties`.
  - Implemented service interfaces.
  - Set up controllers to handle only non-authenticated mappings.
  - Set up `RestExceptionHandler.java` to handle errors for my application.
    - Created some custom runtime exceptions to return respective 4XX errors.
    - Validated error handling through the use of postman

![](https://progress-bar.dev/10?title=Completed&width=100)
- April 12th
  - Created 3 models
    - `AppUser`
    - `Post`
    - `Comment`
  - Created the repositories for the models
  - Created the service files, no impls yet

![](https://progress-bar.dev/5?title=Completed&width=100)
- April 11th
  - Decided to change my project idea as I came up with a more interesting project for me to do.
  - Created the java project with the needed dependencies.
  - Set up the folder structure to easily organize my code.
  - Created the base files.

## User Stories
### AppUser
- As a user, I want to be able to register so that I may use logged-in features.
- As a user, I want to be able to log in with my credentials so that I can access logged-in features.
- As a user, I want to be able to view all the posts I have created so that I can easily manage them from one place.
- As a user, I want to be able to view all the comments I have made so that I can manage them all from one place.
- As a user, I want to be able to create a new post so that I may share what's on my mind, a question, or any of the
other categories available.
- As a user, I want to be able to add a comment to a post so that I may voice my thought on a post.
- As a user, I want to be able to delete my account in case I decide I do not want to have an account anymore.
- As a user, I want to be sure that all of my data is only accessible by myself so that no one else may post or comment
pretending to be me.

### Post
- As a user, I want to be able to explore posts based on category so that I may discover something of interest.
- As a user, I want to be able to view a profile so that I may see more posts
and information from the user.

## Journey
I have a very comfortable grasp with frontend and using Vue. I felt confident
going into this using Vue as my main frontend technology. In terms of backend,
I had a good strong base knowledge in backend as I had done a web internship and
worked making my own personal projects with django in python. The big challenge for
me, was definitely going to be tackling on Java and Java spring, Two completely new 
technologies for me.

When tackling authentication I decided to go with JWT as my previous knowledge
taught me that it is the best use when having a frontend separate from the backend.

Implementing JWT was definitely a big challenge and after successfully,
implementing it, I gained more knowledge as to how exactly JWT works. Before,
I had simply just used a python package that would do all the work for me but,
with Java spring, I myself had to do what those python packages did for me.


## Software Architecture
![Software Architecture](./fullstack_architecture.png?raw=true)

## DB Schema
![Maria DB schema](./db_schema.png?raw=true)
