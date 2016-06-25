package gradebook.api
import grails.plugin.springsecurity.annotation.Secured

@Secured("ROLE_ADMIN")
class SubjectController {

    static scaffold = Subject
}
