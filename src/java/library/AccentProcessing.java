package library;

/**
 *
 * @author ITExplore
 */
public class AccentProcessing {

    class Node
    {
        public Node Next;
        public Character Letter;
        public Character ConvertTo;

        public Node()
        {
            Next = null;
        }

        public Node(Character Letter, Character ConvertTo)
        {
            this.Letter = Letter;
            this.ConvertTo = ConvertTo;
        }

    }

    class AccentTable
    {
        Node START, LAST;

        public AccentTable()
        {
            START = null;
            LAST = null;
        }

        public void insert(Character Letter, Character ConvertTo)
        {
            Node newnode = new Node(Letter, ConvertTo);

            if (START == null)
            {
                START = newnode;
                LAST = newnode;
                return;
            }

            LAST.Next = newnode;
            newnode.Next = null;
            LAST = newnode;
        }

        public char search(char Find)
        {
            for (Node currentNode = START; currentNode != null; currentNode = currentNode.Next)
            {
                if (currentNode.Letter.equals(Find))
                {
                    return currentNode.ConvertTo;
                }
            }

            return Find;
        }
    }

    private AccentTable[] lstAccent;

    public AccentProcessing()
    {
        lstAccent = new AccentTable[7];
        createAccentTable();
    }

    private void createAccentTable(){
        int k = 0;
            do
            {
                lstAccent[k] = new AccentTable();
                k++;
            } while (k < lstAccent.length);

            char[] CaseLetterA = { 'á', 'à', 'ả', 'ã', 'ạ', 'â', 'ấ', 'ầ', 'ẫ', 'ẩ', 'ậ', 'ă', 'ắ', 'ằ', 'ẵ', 'ẳ', 'ặ' };
            char[] CaseLetterD = { 'đ' };
            char[] CaseLetterE = { 'é', 'è', 'ẻ', 'ẽ', 'ẹ', 'ê', 'ế', 'ề', 'ễ', 'ể', 'ệ' };
            char[] CaseLetterI = { 'í', 'ì', 'ĩ', 'ỉ', 'ị' };
            char[] CaseLetterO = { 'ô', 'ơ', 'ó', 'ò', 'ỏ', 'õ', 'ọ', 'ố', 'ồ', 'ổ', 'ỗ', 'ộ', 'ớ', 'ờ', 'ở', 'ỡ', 'ợ' };
            char[] CaseLetterU = { 'ư', 'ú', 'ù', 'ũ', 'ủ', 'ụ', 'ứ', 'ừ', 'ữ', 'ử', 'ự' };
            char[] CaseLetterY = { 'ý', 'ỳ', 'ỷ', 'ỹ', 'ỵ' };

            for (char c : CaseLetterA)
                lstAccent[0].insert(c, 'a');

            for (char c : CaseLetterD)
                lstAccent[1].insert(c, 'd');

            for (char c : CaseLetterE)
                lstAccent[2].insert(c, 'e');

            for (char c : CaseLetterI)
                lstAccent[3].insert(c, 'i');

            for (char c : CaseLetterO)
                lstAccent[4].insert(c, 'o');

            for (char c : CaseLetterU)
                lstAccent[5].insert(c, 'u');

            for (char c : CaseLetterY)
                lstAccent[6].insert(c, 'y');
    }

    public String clearAccent(String ToBeCleared)
        {
            char[] tmp = new char[1];

            for (int i = 0; i < ToBeCleared.length(); i++)
            {
                tmp[0] = ToBeCleared.charAt(i);
                char oldValue = tmp[0];

                //if (String.valueOf(tmp[0]).hashCode() < 8000000) //All letters which is not contain accent, has HashCode < 80000
                    //continue;

                for (int j = 0; j < lstAccent.length; j++)
                {
                    char newValue = tmp[0];
                    newValue = lstAccent[j].search(newValue);
                    ToBeCleared = ToBeCleared.replace(oldValue, newValue);
                }
            }

            return ToBeCleared;
        }

}
