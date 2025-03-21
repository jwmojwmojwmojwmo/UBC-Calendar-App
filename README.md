# My Personal Project

## The UBC Weekly Calendar

This application will allow the user to upload their list of courses provided by Workday, and will auto-generate a weekly calendar, showing class times for each day, as well as a map that visualises where each class is. It is also able to detect classes that may be too close together in time, and give a warning. The user can also add extracurriculars, and the calendar will display this too.  

Any UBC student who wants a fast and easy to use calendar for their classes each day can use this program. 

This project is of interest to me as I have found that using Workday to find classes each day is extremely painful, and I was making a calendar for my first year manually. This way, I can automate the process for the rest of my time here. It also does not require internet except for the initial download from Workday, which will be helpful if I do not have connection as I walk across campus.

User Stories:
- As a user, I want to see exactly what classes I have each day.
- As a user, I want to see where I have to go each day.
- As a user, I want to be able to see a list of what I have to do each day.
- As a user, I want to be able to add extracurrculars and other classes that I have to do on top of my class schedule, and specify the time and location.
- As a user, I want to easily find what I have to do at a specific time.
- As a user, I want to be able to save my calendar at any time.
- As a user, I want to be able to load a saved calendar at any time.
- As a user, I want the calendar to save automatically when I quit the program.

# Instructions for End User

- To start, go to File -> New.
- If you want to import your UBC Schedule, go to Workday -> Academics -> Registration & Courses -> Current Classes -> Settings Button -> Download to Excel.
- Open the Excel file and save it as a .CSV file using the Save As button or an online converter.
- In the program, locate the .CSV file when prompted to.
 You can generate the first required action related to the user story "adding multiple Xs to a Y" by going to File -> New, which will allow you to add a new item.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by left clicking any item, which will allow you to edit its information as well as delete it.
- You can generate the third required action related to the user story "adding multiple Xs to a Y" by right clicking any item, which will allow you to edit its colour.
- You can locate my visual component by using the GUI to see a visualisation of when each items are each day.
- You can save the state of my application by going to File -> Save, which will save the calendar using the calendar's name. To change the calendar's name, go to Edit -> Change Calendar Name.
- You can reload the state of my application by going to File -> Load, which will load the calendar using the calendar's name.