# What is Jenkins ?
Jenkins is an open-source automation server used to implement Continuous Integration (CI) and Continuous Delivery (CD). It acts as a central hub that automates the various stages of the software development lifecycle, from building and testing to deployment.

### The Jenkins Pipeline
Modern Jenkins usage revolves around the Pipeline, which is a set of plugins that provides CI and CD functionality. This is often defined in a Jenkinsfile, allowing you to treat your build process as code (Pipeline as Code).

- **Build:** Compiles the source code.

- **Test:** Runs unit tests, integration tests, and UI tests.

- **Release:** Packages the application (e.g., into a Docker image).

- **Deploy:** Pushes the application to a staging or production environment.

# Configure it with Github

## 1. Using web UI

### Step 1: Install the Git Plugin
Before you begin, ensure Jenkins has the necessary tools to talk to Git.

- Navigate to Manage **Jenkins > Plugins**.

- Select the Available plugins tab.

- Search for "Git".

- Install it (and restart Jenkins if prompted).

### Step 2: Create a New Job
- On the Jenkins dashboard, click New Item.

- Enter a name (e.g., my-automation-job).

- Select Freestyle project (best for simple UI-based config) or Pipeline (best for Jenkinsfile scripts).

- Click OK.

### Step 3: Configure Source Code Management (SCM)
This is where you link your repository.

- Scroll down to the Source Code Management section.

- Select the Git radio button.

- Repository URL: Paste your Git URL (e.g., https://github.com/user/repo.git or your Bitbucket SSH link).

- Credentials: * If the repo is private, click Add > Jenkins.

- Enter your Username and Password (or Secret Text/SSH Key).

- Select those credentials from the dropdown menu.

- Branches to build: Specify the branch (e.g., */main or */develop).

### Step 4: Set the Build Trigger
Decide when Jenkins should start working.

- Go to the Build Triggers section.

- GitHub hook trigger for GITScm polling: Select this if you are using webhooks (recommended).

- Poll SCM: Select this if you want Jenkins to manually check for changes on a timer (e.g., enter `H/15 * * * *` to check every 15 minutes).

### Step 5: Define the Build Steps
Now, tell Jenkins what to do once it has your code.

- Scroll to the Build Steps (or Build Environment in some versions).

- Click Add build step.

- Select Execute shell (for macOS/Linux) or Execute Windows batch command.

- Enter your commands. For example:

```
# Example for a backend project
mvn clean
mvn install
mvn test
mvn compile
mvn package
```

### Step 6: Post-build Actions (Optional)
Click Add post-build action.
- Options include Editable Email Notification to alert the team of a failure or Archive the artifacts to save the compiled .exe or .app file.

### How to Run Your First Build
- Click Save at the bottom of the configuration page.

- On the left-hand sidebar of your job, click Build Now.

- Under Build History, click the progress bar or the build number (e.g., #1).

- Select Console Output to see the real-time logs of Git cloning your code and running your tests.

## Using Jenkinsfile 

### Step 1: Create the Jenkinsfile in Your Repo
First, you need to define the instructions for Jenkins. In the root directory of your Git project, create a file named exactly Jenkinsfile (no file extension).

Add a basic Declarative Pipeline structure:
```
pipeline {
    agent any // Runs on any available executor

    stages {
        stage('Checkout') {
            steps {
                // Pulls the code from the Git repo defined in the job
                checkout scm
            }
        }
        stage('Cleanup') {
            steps {
                // Example: Running a shell command for a backend project
                sh 'mvn clean' 
            }
        }
        stage('Install Dependencies') {
            steps {
                // Example: Running a shell command for a backend project
                sh 'mvn install' 
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Compile') {
            steps {
                sh 'mvn compile'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn package'
            }
        }
    }
    post {
        always {
            echo 'Cleaning up workspace...'
        }
        success {
            echo 'Build and Test passed!'
        }
        failure {
            echo 'Build failed. Check the logs.'
        }
    }
}
### Step 2: Create a Pipeline Job in Jenkins UI
- Open Jenkins and click New Item on the left sidebar.

- Enter a name for your project (e.g., inventory-service-pipeline).

- Select Pipeline and click OK.

### Step 3: Connect Jenkins to Your Git Repository
- Scroll down to the Pipeline section at the bottom of the configuration page.

- In the Definition dropdown, change "Pipeline script" to Pipeline script from SCM.

- In the SCM dropdown, select Git.

- Repository URL: Paste your Git repo link (HTTPS or SSH).

- Credentials: Select your pre-configured credentials (Username/Password or SSH Key).

- Branches to build: Enter the branch you want to track (e.g., */main).

### Step 4: Point to Your Jenkinsfile
- In the Script Path field, ensure it says Jenkinsfile.

- **Note: If your file is inside a subfolder, you would enter path/to/Jenkinsfile **

- Click Save.

### Step 5: Run and Validate
- Click Build Now on the left menu.

- Jenkins will clone your repo, look for the Jenkinsfile, and start executing the stages you defined.

- Stage View: Once the build starts, you will see a visual representation of each stage (Checkout, Test, Build) and how long they took.

