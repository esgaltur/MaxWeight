/**
 * Program module containing services for generating training programs.
 * <p>
 * This module provides services for creating and managing training programs:
 * - ProgramDataService: Provides weight percentages and repetition counts for each week and day
 * - ProgramFactory: Creates training programs using the data from ProgramDataService
 * <p>
 * Dependencies:
 * - core: Uses the domain model classes from the core module
 * <p>
 * This module encapsulates the business logic for generating training programs
 * and should not depend on web or presentation concerns.
 */
package cz.esgaltur.maxweight.program;