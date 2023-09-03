/*
 */
package org.gecko.playground.model.person;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Person</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.playground.model.person.Person#getId <em>Id</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getFirstNames <em>First Names</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getLastName <em>Last Name</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getBirthDate <em>Birth Date</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getAddress <em>Address</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getContact <em>Contact</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getTag <em>Tag</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Person#getComment <em>Comment</em>}</li>
 * </ul>
 *
 * @see org.gecko.playground.model.person.PersonPackage#getPerson()
 * @model
 * @generated
 */
@ProviderType
public interface Person extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_Id()
	 * @model id="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Person#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>First Names</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Names</em>' attribute.
	 * @see #setFirstNames(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_FirstNames()
	 * @model
	 * @generated
	 */
	String getFirstNames();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Person#getFirstNames <em>First Names</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Names</em>' attribute.
	 * @see #getFirstNames()
	 * @generated
	 */
	void setFirstNames(String value);

	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_LastName()
	 * @model
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Person#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>Birth Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Birth Date</em>' attribute.
	 * @see #setBirthDate(Date)
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_BirthDate()
	 * @model
	 * @generated
	 */
	Date getBirthDate();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Person#getBirthDate <em>Birth Date</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Birth Date</em>' attribute.
	 * @see #getBirthDate()
	 * @generated
	 */
	void setBirthDate(Date value);

	/**
	 * Returns the value of the '<em><b>Address</b></em>' reference list.
	 * The list contents are of type {@link org.gecko.playground.model.person.Address}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Address</em>' reference list.
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_Address()
	 * @model
	 * @generated
	 */
	EList<Address> getAddress();

	/**
	 * Returns the value of the '<em><b>Contact</b></em>' containment reference list.
	 * The list contents are of type {@link org.gecko.playground.model.person.Contact}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Contact</em>' containment reference list.
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_Contact()
	 * @model containment="true"
	 * @generated
	 */
	EList<Contact> getContact();

	/**
	 * Returns the value of the '<em><b>Tag</b></em>' reference list.
	 * The list contents are of type {@link org.gecko.playground.model.person.Tag}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tag</em>' reference list.
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_Tag()
	 * @model
	 * @generated
	 */
	EList<Tag> getTag();

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getPerson_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Person#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

} // Person
