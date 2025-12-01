# SnackOverflow ~ _Your next best dish_

We are the SnackOverflow Team! Our goal? Make you find your next meal in an intuitive app.

---
## Features and Functionalities

The SnackOverflow app has a variety of functionalities to respond to all your culinary needs thanks to the Spoonacular 
Meal API (see below for more details).

We have implemented a complete search tool which accepts any ingredients, cooking time, cuisine and meal type, as well 
as dietary restrictions and allergens.

Alongside that, search results may be viewed to get fuller details on the recipe, namely a full list of ingredients and
quantities, as well as the instructions to prepare the meal.

What if a recipe sounds interesting but is too time-consuming to prepare immediately? SnackOverflow gives you the option
of saving recipes for later reference, and are available in the Saved section of the app, available from the sidebar.

When saved, you may also add your own tags to recipes for easier access from the Saved page, which allows you to filter
recipes by tags.

Meal planning can be a complicated process, which is why we have also prepared SnackOverflow to help with meal planning:
input a dietary restrictions, a calorie level, and a number of meals per day (from 1 to 3), and SnackOverflow will 
generate a plan for all the meals of your week in a matter of seconds.

Want to make a new recipe that is just your own? SnackOverflow has you covered: the Create New page is where you will be
able to design your own recipes by giving them a name, serving size, cuisine, cooking time, meal type, and tags, as well
as ingredients list and instructions.

All of these functionalities are also bundled within an account system with username and password, where saved and 
user-created recipes are stored in our database. To find your saved recipes, you just need to log into your account!

---

## User Stories

The user stories we have accounted for when designing SnackOverflow are the following:

1) As a user, I sometimes have trouble coming up with recipes with the  ingredient that I already have.


2) As a user, I want to filter by recipe requirements like cooking time, type of cuisine, etc.


3) As a user, I want to save my recipes by having my own account and a login/log out feature.


4) As a user, I want to view the exact details of a recipe, and be able to view similar recipes after opening one so I 
can explore alternatives.


5) As a user, I want to tag recipes with keywords (e.g., “quick,” “budget,” “holiday”) so I can filter by mood or occasion.


6) As a user, I want to be able to generate meal plans to match my nutrition requirements

---
## Spoonacular API

This project uses the Spoonacular API, which provides numerous functionalities to search recipes. We have implemented a
Dotenv in order to keep API keys private and secure, and use only this API for all data we are fetching to make 
SnackOverflow's functionalities.

The documentation for this API can be found here: [Spoonacular API Documentation](https://spoonacular.com/food-api/docs)

---
## MongoDB

The tool we use in SnackOverflow for database matters is MongoDB. We use this database for the user registration, 
log in/out, saving recipes and user-created recipes.


The documentation for this API can be found here: [MongoDB Documentation](https://www.mongodb.com/docs/)

---
## Clean Architecture

The entirety of SnackOverflow was designed with Clean Architecture in mind. Our idea with this decision was that it 
would allow us to present a finished product while also keeping the door open for future expansions, would these be 
adding more functionalities for the user stories presented above, or completely new user stories which we could respond
to thanks to the Spoonacular API and MongoDB.

---
## Possibilities for future expansions

SnackOverflow has reached release possibility, but we still have many more ideas which could be implemented in the 
future. Thanks to our application of Clean Architecture, our code is easily opened to expansion and additional user 
stories.

- [4] Adding a "preference" system for weighted similarities
- [6] Adding a print (to PDF) mechanism for recipes
- [6] Let the user add notes/ratings to recipes
- [2] add more filters based on the many more details returned by API calls
