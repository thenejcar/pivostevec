package si.kisek.pivovarna.pivostevec.utils;

import java.util.Comparator;

import si.kisek.pivovarna.pivostevec.models.Runda;

/**
 * Created by Tone on 2.4.2016.
 */
public class RundaDateComparator implements Comparator<Runda> {
        @Override
        public int compare(Runda lhs, Runda rhs) {
            if(lhs.getDate().getTime() < rhs.getDate().getTime())
                return -1;
            else if (lhs.getDate().getTime() > rhs.getDate().getTime())
                return 1;
            else
                return 0;
        }
}
