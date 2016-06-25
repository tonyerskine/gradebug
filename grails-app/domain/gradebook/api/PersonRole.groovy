package gradebook.api

import grails.gorm.DetachedCriteria
import groovy.transform.ToString

import org.apache.commons.lang.builder.HashCodeBuilder

@ToString(cache=true, includeNames=true, includePackage=false)
class PersonRole implements Serializable {

	private static final long serialVersionUID = 1

	Person person
	Role role

	@Override
	boolean equals(other) {
		if (other instanceof PersonRole) {
			other.personId == person?.id && other.roleId == role?.id
		}
	}

	@Override
	int hashCode() {
		def builder = new HashCodeBuilder()
		if (person) builder.append(person.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static PersonRole get(long personId, long roleId) {
		criteriaFor(personId, roleId).get()
	}

	static boolean exists(long personId, long roleId) {
		criteriaFor(personId, roleId).count()
	}

	private static DetachedCriteria criteriaFor(long personId, long roleId) {
		PersonRole.where {
			person == Person.load(personId) &&
			role == Role.load(roleId)
		}
	}

	static PersonRole create(Person person, Role role) {
		def instance = new PersonRole(person: person, role: role)
		instance.save()
		instance
	}

	static boolean remove(Person u, Role r) {
		if (u != null && r != null) {
			PersonRole.where { person == u && role == r }.deleteAll()
		}
	}

	static int removeAll(Person u) {
		u == null ? 0 : PersonRole.where { person == u }.deleteAll()
	}

	static int removeAll(Role r) {
		r == null ? 0 : PersonRole.where { role == r }.deleteAll()
	}

	static constraints = {
		role validator: { Role r, PersonRole ur ->
			if (ur.person?.id) {
				PersonRole.withNewSession {
					if (PersonRole.exists(ur.person.id, r.id)) {
						return ['userRole.exists']
					}
				}
			}
		}
	}

	static mapping = {
		id composite: ['person', 'role']
		version false
	}
}
