package com.hans.jenkins

/**
 *
 * `apps` is a list of objects with name, dest, and dir keys, corresponding to the application name,
 * the installation destination (on instances) and directory of the packaged artifact (from e.g. mvn package).
 *
 */
class App implements Serializable  {

    public static final HashMap list = [
       
		'rest-service': [
			dest: '/user/extrabases-service',
			dir: './build/libs',
			repository: 'https://github.com/narendrak144/rest-service.git'
		]
    ]

    public def name, dest, dir, repository, version

    /**
     * Assigns a version in addition to the constant data from the config above.
     * @param name - string key corresponding to {list} above.
     */
    public App(def name) {
        final def app = list.get(name)
		//println app
        this.name = name
        this.dest = app.dest
        this.dir = app.dir
        this.repository = app.repository
        def date = new Date()
        def version = date.format("yyyyMMddHHmmss")
        this.version = version
    }

    /**
     * @return newline separated application name list for jenkins selection parameter.
     */
    public static final String names() {
        return new ArrayList<String>(list.keySet()).join("\n")
    }

    /**
     * The plain version does not have -1 suffixed.
     * This will be for the deploy job parameter.
     * @return
     */
    public final String getRpmVersion() {
        return version + '-1'
    }

	@Override
	public String toString() {
		return "App [name=" + name + ", dest=" + dest + ", dir=" + dir + ", repository=" + repository + ", version="
				+ version + "]";
	}
	
	

}
