#include <stdio.h>

main ()
{ int cmd, v1, v2;

  do
  { printf ("command> ");
    scanf ("%d %d %d", &cmd, &v1, &v2);
    printf ("%d: %d %d\n", cmd, v1, v2);
    if (cmd == 1) solve_quadratic (v1, v2);
    else if (cmd == 2) find_triples (v1, v2);
    else if (cmd == 3) modular_sum (v1, v2);
    else if (cmd !=0) printf ("Invalid command\n");
  }
  while (cmd !=0);
}

