/**
 * Copyright (C) 2016 LibRec
 * 
 * This file is part of LibRec.
 * LibRec is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * LibRec is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with LibRec. If not, see <http://www.gnu.org/licenses/>.
 */
package net.librec.data;

import java.io.Serializable;

import net.librec.common.AbstractContext;
import net.librec.conf.Configuration;

/**
 * Data Context
 * 
 * @author WangYuFeng
 */
public class DataContext extends AbstractContext implements Serializable {

    public DataContext() {
        super();
    }

    public DataContext(Configuration conf) {
        super();
        this.conf = conf;
    }

}
