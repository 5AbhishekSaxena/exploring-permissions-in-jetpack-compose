# Exploring Permissions In Jetpack Compose

### Problem Statement

I have a list of items that is displayed on the screen and I want to export it to an excel file.

Steps
- Check for external storage permission.
   - If permission is not granted - Request for the permission at runtime.
   - If granted, continue
- Initialize the flow to export the data to an excel file.
   - The list that has to be exported is in the `ViewModel`.

#### Observation
- The `HomeContent/HomeScreen` is recomposed when the permission is requested and when it is granted/rejected.

#### Question(s)
- How to avoid unnecessary layout recompsing when the data is present in the `HomeViewModel` and it has nothing to do with the UI?
- Does the reomposition affect the performance of the application? If so, then how to improve it?
