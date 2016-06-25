import gradebook.api.Person
import gradebook.api.PersonRole
import gradebook.api.Role

class BootStrap {

    def init = { servletContext ->

        if (!Role.count()) {
            Role admin = new Role(authority: "ROLE_ADMIN").save(failOnError: true)
            Role teacher = new Role(authority: "ROLE_TEACHER").save(failOnError: true)
            Role student = new Role(authority: "ROLE_STUDENT").save(failOnError: true)
            Role user = new Role(authority: "ROLE_USER").save(failOnError: true)

            Person bacon = new Person(username: "bacon", password: ".").save(failOnError: true)

            new PersonRole(person: bacon, role: admin).save(failOnError: true)
            new PersonRole(person: bacon, role: teacher).save(failOnError: true)
            new PersonRole(person: bacon, role: student).save(failOnError: true)
            new PersonRole(person: bacon, role: user).save(failOnError: true)

        }

    }
    def destroy = {
    }
}
