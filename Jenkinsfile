node {

    def mvnHome = tool name: 'maven 3.9.14', type: 'maven'
    
    stage('Checkout Code') {
        echo "Checking out code from GitHub..."

        git branch: 'main',
            credentialsId: 'github',
            url: 'git@github.com:tsrvanam/calculator-app.git'
    }

    stage('Verify Maven') {
        echo "Checking Maven version..."
        sh "${mvnHome}/bin/mvn -version"
    }

    stage('Build WAR') {
        echo "Building WAR file..."
        sh "${mvnHome}/bin/mvn clean package"
    }

    stage('SonarQube Analysis') {
            withSonarQubeEnv('sonarqube-server') {
                sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.projectKey=calculator-app"
            }
        }

    stage('Archive Artifact') {
        echo "Archiving WAR file..."
        archiveArtifacts artifacts: '**/*.war', fingerprint: true
    }
    

    // Post actions (Scripted way using try/catch)
    try {
        echo "Pipeline completed successfully 🎉"
    } catch (Exception e) {
        echo "Pipeline failed ❌"
        throw e
    }
}
