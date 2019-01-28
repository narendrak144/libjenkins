package test

import com.hans.jenkins.App;
import com.hans.jenkins.scripts.Integration

class JenkinsAppTest {

	static void main(def args) {

		def appNames = 'comptix-teamsite';
		
		def apps = Integration.getAppMetaData(appNames)
		
		
		
		
	}
}
