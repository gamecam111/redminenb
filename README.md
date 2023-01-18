RedmineNB
=========
Redmine NB plugin migrated to NetBeans 16

This repository is derived from the  [RedmineNB Kenai project](https://kenai.com/projects/redminenb/)  and  [RedmineNB Project from 2016](https://github.com/redminenb/redminenb). This project is built on NetBeans 16.0


Development
-----------

The plugin is developed as a maven project. The plugin project can directly be opened in netbeans via "Open Project" and you are good to go. You can modify and build it directly from within the IDE.

Bug fix
-----------

- Fix dependencies (version, replacement etc..)
- Fix picture bug (Images were not loading in the task view) - Fixed by downloading images to cache. The cache folder can be set in PathSettings.java.
