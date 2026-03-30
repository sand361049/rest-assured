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

- Select **Freestyle project** (best for simple UI-based config) or **Pipeline** (best for Jenkinsfile scripts).

- Click OK.

### Step 3: Configure Source Code Management (SCM)
This is where you link your repository.

- Scroll down to the Source Code Management section.

- Select the **Git** radio button.

- Repository URL: Paste your Git URL (e.g., https://github.com/user/repo.git or your Bitbucket SSH link).

- Credentials: * If the repo is private, click Add > Jenkins.

- Enter your **Username** and **Password** (or Secret Text/SSH Key).

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
```
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

## Explanation of Jenkinsfile 

### 1. The Environment (agent)
agent `any`: This tells Jenkins to run this pipeline on any available node (agent) in the Jenkins environment. If you have multiple servers connected to Jenkins, it will pick the first one with a free "executor" slot.

### 2. The Sequence of Work (stages)
The stages block contains the logical steps of your CI/CD process. Each stage shows up as a separate column in the Jenkins UI, making it easy to see where a build failed.

- **stage('Checkout')**: Jenkins connects to the Git repository you configured in the Web UI. The command checkout scm pulls the specific branch and commit that triggered the build.

- **stage('Cleanup')**: Runs `mvn clean`. This is a Maven command that deletes the target folder from previous builds, ensuring you are starting from a fresh state.

- **stage('Install Dependencies')**: Runs `mvn install`. This downloads necessary libraries and installs the project into the local repository so other stages can use them.

- **stage('Test')**: Runs `mvn test`. This executes your unit tests. Crucially, if any test fails, Jenkins stops the pipeline here and marks the build as "Failed."

- **stage('Compile')**: Runs `mvn compile`. This compiles the source code of the project into Java bytecode.

- **stage('Package')**: Runs `mvn package`. This takes the compiled code and wraps it into its distributable format, such as a .jar or .war file.

### 3. The Execution Steps (steps)
Inside every stage is a steps block. This is where the actual "work" happens.

**sh '...'**: This tells Jenkins to execute a shell command. On a macOS or Linux runner, it opens a terminal and runs the command (like mvn test). On Windows, you would typically use **bat** instead of **sh**.

### 4. Final Actions (post)
The post section runs after the stages have finished. It is used for `notifications`, `cleanup`, or `reporting`.

- **always**: These steps run regardless of whether the build passed or failed. It's perfect for cleaning up temporary files or workspace logs.

- **success**: Runs only if every single stage completed successfully. This is usually where you would trigger a deployment or send a "Build Passed" Slack message.

- **failure**: Runs if any stage fails. This is vital for alerting the team via email or messaging apps so they can fix the code immediately.

## What are Poll SCM and  Github hook Trigger

These are the two primary ways Jenkins "knows" when to start a build after you commit code. The main difference is who starts the conversation: Jenkins asking Git (Polling) or Git telling Jenkins (Webhooks).

### 1. Poll SCM (The "Pull" Method)
In this setup, Jenkins acts like an eager assistant who checks your repository on a fixed schedule to see if anything has changed.

**How it works**: You define a schedule using Cron syntax (e.g., every 5 minutes). Jenkins connects to the Git repo, compares the latest commit hash with the previous one, and if they differ, it triggers a build.

**Cron Example**: `H/5 * * * *` means "Check every 5 minutes."

**Best For**: * Internal networks where your Git server (like an on-prem Bitbucket) cannot "see" your Jenkins server over the internet.

Legacy systems that don't support Webhooks.

**Downside**: It is resource-heavy. If you have 100 jobs polling every minute, your network and CPU will be constantly busy even if no one is coding. It also creates a delay (if you push right after a poll, you wait 5 minutes for the next one).

### 2. GitHub Hook Trigger (The "Push" Method)
This is the modern, efficient approach. Instead of Jenkins checking constantly, Git sends a notification to Jenkins the split-second a push occurs.

**How it works**: 
1.  You check the box "GitHub hook trigger for GITScm polling" in Jenkins.
2.  You go to your GitHub/Bitbucket repository settings and add a Webhook.
3.  You provide Jenkins' URL (e.g., https://jenkins.mycompany.com/github-webhook/).
4.  Whenever a developer pushes code, GitHub sends an HTTP POST request to that URL, and Jenkins starts the build immediately.

**Best For**: * Most modern DevOps setups.

Teams that want instant feedback on their commits.

**Downside**: Requires your Jenkins server to be reachable by GitHub (or whichever Git provider you use). If Jenkins is behind a strict firewall, Webhooks won't work without a proxy or tunnel.
