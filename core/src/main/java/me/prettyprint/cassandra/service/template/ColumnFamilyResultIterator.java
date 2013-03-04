/**
 * This class will instill 'normal' iterator behavior to a ColumnFamilyResult.
 * Simply instantiate this class while passing your ColumnFamilyResult as a 
 * constructor argument.
 * 
 * Ex.
 * 
 * ColumnFamilyResultIterator myResultsInterator = 
 *      new ColumnFamilyResultIterator(someColumnFamilyResult);
 * 
 * You can then use myResultsInterator with for loops or iterate with a while loop 
 * just as with any standard java iterator. 
 * 
 */
package me.prettyprint.cassandra.service.template;

import java.util.Iterator;

import me.prettyprint.cassandra.service.template.ColumnFamilyResult;

public class ColumnFamilyResultIterator implements Iterator<ColumnFamilyResult<?,?>> {
        private ColumnFamilyResult<?, ?> res;
        private boolean isStart = true;

        public ColumnFamilyResultIterator(ColumnFamilyResult<?, ?> res) {
            this.res = res;
        }
        
		public boolean hasNext() {
			boolean retval = false;
			if (isStart) {
				if(res.hasResults() || res.hasNext()) {
					retval = true; 
				}
			} 
			else {
				retval = res.hasNext();
			}
			return retval;
		}


        public ColumnFamilyResult<?, ?> getRes() {
            return res;
        }

        public void setRes(ColumnFamilyResult<?, ?> res) {
            this.res = res;
        }

        public ColumnFamilyResult<?, ?> next()
        {
            if (isStart) {
                isStart = false;
                return res;
            }
            else {
                return (ColumnFamilyResult<?, ?>) res.next();
            }
        }
        
        public void remove() {
            res.remove();
        }
}
