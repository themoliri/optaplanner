/*
 * Copyright 2010 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.optaplanner.examples.manners2009.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.drools.planner.api.domain.solution.PlanningEntityCollectionProperty;
import org.drools.planner.api.domain.solution.PlanningSolution;
import org.drools.planner.core.score.buildin.simple.SimpleScore;
import org.drools.planner.core.score.buildin.simple.SimpleScoreDefinition;
import org.drools.planner.core.solution.Solution;
import org.optaplanner.examples.common.domain.AbstractPersistable;
import org.optaplanner.persistence.xstream.XStreamScoreConverter;

@PlanningSolution
@XStreamAlias("Manners2009")
public class Manners2009 extends AbstractPersistable implements Solution<SimpleScore> {

    private List<Job> jobList;
    private List<Guest> guestList;
    private List<HobbyPractician> hobbyPracticianList;
    private List<Table> tableList;
    private List<Seat> seatList;

    private List<SeatDesignation> seatDesignationList;

    @XStreamConverter(value = XStreamScoreConverter.class, types = {SimpleScoreDefinition.class})
    private SimpleScore score;

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }

    public List<Guest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<Guest> guestList) {
        this.guestList = guestList;
    }

    public List<HobbyPractician> getHobbyPracticianList() {
        return hobbyPracticianList;
    }

    public void setHobbyPracticianList(List<HobbyPractician> hobbyPracticianList) {
        this.hobbyPracticianList = hobbyPracticianList;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }

    public List<Seat> getSeatList() {
        return seatList;
    }

    public void setSeatList(List<Seat> seatList) {
        this.seatList = seatList;
    }

    @PlanningEntityCollectionProperty
    public List<SeatDesignation> getSeatDesignationList() {
        return seatDesignationList;
    }

    public void setSeatDesignationList(List<SeatDesignation> seatDesignationList) {
        this.seatDesignationList = seatDesignationList;
    }

    public SimpleScore getScore() {
        return score;
    }

    public void setScore(SimpleScore score) {
        this.score = score;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public Collection<? extends Object> getProblemFacts() {
        List<Object> facts = new ArrayList<Object>();
        facts.addAll(EnumSet.allOf(JobType.class));
        facts.addAll(jobList);
        facts.addAll(guestList);
        facts.addAll(EnumSet.allOf(Hobby.class));
        facts.addAll(hobbyPracticianList);
        facts.addAll(tableList);
        facts.addAll(seatList);
        // Do not add the planning entity's (seatDesignationList) because that will be done automatically
        return facts;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (id == null || !(o instanceof Manners2009)) {
            return false;
        } else {
            Manners2009 other = (Manners2009) o;
            if (seatDesignationList.size() != other.seatDesignationList.size()) {
                return false;
            }
            for (Iterator<SeatDesignation> it = seatDesignationList.iterator(), otherIt = other.seatDesignationList.iterator(); it.hasNext();) {
                SeatDesignation seatDesignation = it.next();
                SeatDesignation otherSeatDesignation = otherIt.next();
                // Notice: we don't use equals()
                if (!seatDesignation.solutionEquals(otherSeatDesignation)) {
                    return false;
                }
            }
            return true;
        }
    }

    public int hashCode() {
        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();
        for (SeatDesignation seatDesignation : seatDesignationList) {
            // Notice: we don't use hashCode()
            hashCodeBuilder.append(seatDesignation.solutionHashCode());
        }
        return hashCodeBuilder.toHashCode();
    }

}