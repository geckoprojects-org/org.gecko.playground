/*
 */
package org.gecko.playground.model.person;

import org.eclipse.emf.ecore.EObject;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Contact</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.gecko.playground.model.person.Contact#getValue <em>Value</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Contact#getComment <em>Comment</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Contact#getType <em>Type</em>}</li>
 *   <li>{@link org.gecko.playground.model.person.Contact#getContext <em>Context</em>}</li>
 * </ul>
 *
 * @see org.gecko.playground.model.person.PersonPackage#getContact()
 * @model
 * @generated
 */
@ProviderType
public interface Contact extends EObject {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getContact_Value()
	 * @model required="true"
	 * @generated
	 */
	String getValue();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Contact#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(String value);

	/**
	 * Returns the value of the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Comment</em>' attribute.
	 * @see #setComment(String)
	 * @see org.gecko.playground.model.person.PersonPackage#getContact_Comment()
	 * @model
	 * @generated
	 */
	String getComment();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Contact#getComment <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Comment</em>' attribute.
	 * @see #getComment()
	 * @generated
	 */
	void setComment(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.playground.model.person.ContactType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see org.gecko.playground.model.person.ContactType
	 * @see #setType(ContactType)
	 * @see org.gecko.playground.model.person.PersonPackage#getContact_Type()
	 * @model required="true"
	 * @generated
	 */
	ContactType getType();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Contact#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see org.gecko.playground.model.person.ContactType
	 * @see #getType()
	 * @generated
	 */
	void setType(ContactType value);

	/**
	 * Returns the value of the '<em><b>Context</b></em>' attribute.
	 * The literals are from the enumeration {@link org.gecko.playground.model.person.ContextType}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' attribute.
	 * @see org.gecko.playground.model.person.ContextType
	 * @see #setContext(ContextType)
	 * @see org.gecko.playground.model.person.PersonPackage#getContact_Context()
	 * @model required="true"
	 * @generated
	 */
	ContextType getContext();

	/**
	 * Sets the value of the '{@link org.gecko.playground.model.person.Contact#getContext <em>Context</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' attribute.
	 * @see org.gecko.playground.model.person.ContextType
	 * @see #getContext()
	 * @generated
	 */
	void setContext(ContextType value);

} // Contact
