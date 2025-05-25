/**
 * Web module containing controllers for the web interface and REST API.
 * <p>
 * This module provides controllers for handling HTTP requests:
 * - WebController: Handles requests for the web interface and returns Thymeleaf views
 * - ApiController: Provides a REST API for accessing training program data programmatically
 * <p>
 * Dependencies:
 * - core: Uses the domain model classes from the core module
 * - program: Uses the services from the program module to generate training programs
 * <p>
 * This module is responsible for the presentation layer of the application
 * and should not contain business logic.
 */
package cz.esgaltur.maxweight.web;